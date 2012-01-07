package functiongenerator.gp.functions;

import ec.EvolutionState;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ec.util.Parameter;

public abstract class BinaryOperation extends GPNode {

	@Override
	public void checkConstraints(final EvolutionState state, final int tree,
			final GPIndividual typicalIndividual, final Parameter individualBase) {
		super.checkConstraints(state, tree, typicalIndividual, individualBase);
		
		if (children.length != 2) {
			state.output.error("Incorrect number of children for node " + toStringForError()
					+ " at " + individualBase);
		}
	}
	
}
