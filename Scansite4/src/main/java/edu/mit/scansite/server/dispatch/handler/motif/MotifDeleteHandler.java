package edu.mit.scansite.server.dispatch.handler.motif;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletContext;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Provider;

import edu.mit.scansite.server.ServiceLocator;
import edu.mit.scansite.server.dispatch.BootstrapListener;
import edu.mit.scansite.shared.dispatch.motif.LightWeightMotifRetrieverResult;
import edu.mit.scansite.shared.dispatch.motif.MotifDeleteAction;
import edu.mit.scansite.shared.transferobjects.LightWeightMotif;
import edu.mit.scansite.shared.transferobjects.Motif;

/**
 * @author Tobieh
 * @author Konstantin Krismer
 */
public class MotifDeleteHandler implements
		ActionHandler<MotifDeleteAction, LightWeightMotifRetrieverResult> {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final Provider<ServletContext> contextProvider;

	@Inject
	public MotifDeleteHandler(final Provider<ServletContext> contextProvider) {
		this.contextProvider = contextProvider;
	}

	@Override
	public Class<MotifDeleteAction> getActionType() {
		return MotifDeleteAction.class;
	}

	@Override
	public LightWeightMotifRetrieverResult execute(MotifDeleteAction action,
			ExecutionContext context) throws DispatchException {
		try {
			ServiceLocator.getDaoFactory().getMotifDao().deleteMotif(action.getMotifId());
			List<Motif> motifs = ServiceLocator.getDaoFactory().getMotifDao()
					.getAll(action.getMotifClass(), false);
			List<LightWeightMotif> lightWeightMotifs = new LinkedList<LightWeightMotif>();
			for (Motif motif : motifs) {
				lightWeightMotifs.add(new LightWeightMotif(motif.getId(), motif
						.getDisplayName(), motif.getShortName()));
			}
			return new LightWeightMotifRetrieverResult(lightWeightMotifs);
		} catch (Exception e) {
			logger.error("Error deleting motif: " + e.getMessage());
			throw new ActionException(e.getMessage(), e);
		}
	}

	@Override
	public void rollback(MotifDeleteAction action,
			LightWeightMotifRetrieverResult result, ExecutionContext context)
			throws DispatchException {
	}
}
