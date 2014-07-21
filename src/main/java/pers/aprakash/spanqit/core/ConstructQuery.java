package pers.aprakash.spanqit.core;

import pers.aprakash.spanqit.graphpattern.GraphTemplate;
import pers.aprakash.spanqit.graphpattern.TriplePattern;

/**
 * The SPARQL CONSTRUCT query
 * 
 * @author Ankit
 *
 * @see <a
 *      href="http://www.w3.org/TR/2013/REC-sparql11-query-20130321/#construct">
 *      SPARQL CONSTRUCT Query</a>
 */
public class ConstructQuery extends BaseQuery<ConstructQuery> {
	private GraphTemplate construct = new GraphTemplate();

	/**
	 * Add triples to this query's graph template
	 * 
	 * @param patterns
	 *            the triples to include in the graph template
	 * @return this
	 */
	public ConstructQuery construct(TriplePattern... patterns) {
		construct.construct(patterns);

		return this;
	}

	@Override
	protected String getQueryActionString() {
		return construct.getQueryString();
	}
}