package functiongenerator.core;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.LogFactory;

import ec.EvolutionState;
import ec.Evolve;
import ec.gp.GPIndividual;
import ec.simple.SimpleStatistics;
import ec.util.Log;
import ec.util.LogRestarter;
import ec.util.ParameterDatabase;
import functiongenerator.core.gp.problem.AbstractRegressionProblem;
import functiongenerator.ui.printing.JavaSourceCodeGenerator;

/**
 * Responsible for running the GP algorithm.
 */
public class Engine {

    static private final org.apache.commons.logging.Log logger = LogFactory.getLog(Engine.class);

    private List<Number[]> points = new ArrayList<Number[]>();
    private Settings settings;
    private List<IProgressListener> listeners = new ArrayList<IProgressListener>();

    private volatile boolean cancel = false;

    private AbstractRegressionProblem problem;

    private EvolutionState state;

    private StringBuffer buffer;

    private int numberOfXes;

    /**
     * Returns the current problem which is being processes by engine. Before
     * initiation phase of the {@linkplain Engine} returns null.
     * 
     * <p>
     * Initiation Phase
     * </p>
     */
    public AbstractRegressionProblem getProblem() {
        return problem;
    }

    public List<Number[]> getPoints() {
        return points;
    }

    public void setPoints(List<Number[]> points) {
        this.points = points;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public void addListener(IProgressListener listener) {
        listeners.add(listener);
    }

    public void removeListener(IProgressListener listener) {
        listeners.remove(listener);
    }

    private void updateProgress(String output, EvolutionStateHelper stateHelper) {
        for (IProgressListener listener : listeners) {
            listener.update(output, stateHelper);
        }
    }

    /**
     * Initializes the all possible elements
     * 
     * @throws Exception
     */
    @SuppressWarnings("serial")
    public void init() throws Exception {
        cancel = false;

        numberOfXes = points.get(0).length - 1;

        ProblemType type = null;
        if (points.get(0)[0] instanceof Double) {
            logger.info("Problem is REAL numbered");
            type = ProblemType.DOUBLE;
        } else {
            logger.info("Problem is INTEGER numbered");
            type = ProblemType.INTEGER;
        }

        ParameterDatabase db = getSettings().generateParameterDatabase(numberOfXes, type);
        state = Evolve.initialize(db, 0);
        state.startFresh();

        problem = (AbstractRegressionProblem) state.evaluator.p_problem;
        problem.setPoints(points);
        problem.setMaxTreeDepth(getSettings().getMaxTreeDepth());

        StringWriter writer = new StringWriter();
        buffer = writer.getBuffer();

        state.output.addLog(writer, new LogRestarter() {

            @Override
            public Log restart(Log l) throws IOException {
                return l;
            }

            @Override
            public Log reopen(Log l) throws IOException {
                return l;
            }
        }, true, false);

    }

    /**
     * Runs the computations
     */
    public String run() throws Exception {

        /* the big loop */
        int result = EvolutionState.R_NOTDONE;
        EvolutionStateHelper stateHelper = new EvolutionStateHelper(state);
        while (result == EvolutionState.R_NOTDONE && !cancel) {
            result = state.evolve();

            stateHelper.getBesIndividual();
            updateProgress(buffer.toString(), stateHelper);

            // this method is for buffer
            buffer.delete(0, buffer.length());
        }

        if (cancel) {
            state.finish(result);
            Evolve.cleanup(state);
            return null;
        }

        SimpleStatistics stat = (SimpleStatistics) state.statistics;
        GPIndividual ind = (GPIndividual) stat.best_of_run[0];
        String template = JavaSourceCodeGenerator.generateClassTemplate(settings, ind, numberOfXes);

        state.finish(result);
        Evolve.cleanup(state);

        return template;
    }

    public void cancel() {
        cancel = true;
    }
}
