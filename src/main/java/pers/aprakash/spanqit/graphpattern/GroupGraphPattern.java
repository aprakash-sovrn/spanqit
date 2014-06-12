package pers.aprakash.spanqit.graphpattern;


import pers.aprakash.spanqit.constraint.Expression;
import pers.aprakash.spanqit.core.QueryElementCollection;
import pers.aprakash.spanqit.core.Util;

class GroupGraphPattern extends QueryElementCollection<GraphPatternIface>
		implements GraphPatternIface {
	private static final String OPTIONAL = "OPTIONAL";
	private static final String DELIMITER = " . ";
	private Filter filter;
	protected boolean isOptional;

	GroupGraphPattern() {
		this(false);
	}

	GroupGraphPattern(boolean isOptional) {
		super(DELIMITER);
		this.isOptional = isOptional;
	}

	GroupGraphPattern(GraphPatternIface pattern) {
		super(DELIMITER);

		if (pattern instanceof GroupGraphPattern) {
			copy((GroupGraphPattern) pattern);
		} else {
			this.isOptional = false;
			elements.add(pattern);
		}
	}

	private void copy(GroupGraphPattern original) {
		this.elements = original.elements;
		this.isOptional = original.isOptional;
		this.filter = original.filter;
	}

	GroupGraphPattern and(GraphPatternIface... patterns) {
		for (GraphPatternIface pattern : patterns) {
			elements.add(pattern);
		}

		return this;
	}
	
	GroupGraphPattern optional(boolean isOptional) {
		this.isOptional = isOptional;

		return this;
	}

	GroupGraphPattern filter(Expression<?> constraint) {
		if (filter == null) {
			filter = new Filter();
		}
		filter.setConstraint(constraint);

		return this;
	}

	@Override
	public String getQueryString() {
		StringBuilder pattern = new StringBuilder();
		StringBuilder innerPattern = new StringBuilder();

		if (isOptional) {
			pattern.append(OPTIONAL + " ");
		}

		innerPattern.append(super.getQueryString());

		if (filter != null) {
			innerPattern.append("\n").append(filter.getQueryString());
		}

		return pattern.append(Util.getBracketedString(innerPattern.toString()))
				.toString();
	}

	@Override
	public String getPrettyQueryString(int indent) {
		int subIndent = 0;
		StringBuilder pattern = new StringBuilder();
		StringBuilder innerPattern = new StringBuilder();

		if (isOptional) {
			pattern.append(OPTIONAL + " ");
			subIndent += OPTIONAL.length() + 4;
		}

		innerPattern.append(super.getPrettyQueryString(indent + subIndent));

		if (filter != null) {
			innerPattern.append("\n")
					.append(Util.getIndent(indent + subIndent))
					.append(filter.getQueryString());
		}

		return pattern.append(
				Util.getPrettyBracketedString(innerPattern.toString(), 0))
				.toString();
	}
}