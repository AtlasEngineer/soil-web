/**
 * Copyright (c) 2015-2017, Henry Yang 杨勇 (gismail@foxmail.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.atlas.server.model.sql;

import com.lambkit.db.sql.ConditionMode;
import com.lambkit.db.sql.column.Column;
import com.lambkit.db.sql.column.Columns;
import com.lambkit.db.sql.column.Example;

import java.io.Serializable;
import java.util.List;

/**
 * @author yangyong 
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-04-03
 * @version 1.0
 * @since 1.0
 */
public class IntegralCriteria extends Columns implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static IntegralCriteria create() {
		return new IntegralCriteria();
	}
	
	public static IntegralCriteria create(Column column) {
		IntegralCriteria that = new IntegralCriteria();
		that.add(column);
        return that;
    }

    public static IntegralCriteria create(String name, Object value) {
        return (IntegralCriteria) create().eq(name, value);
    }
    
    public Example example() {
    	return Example.create("at_integral", this);
    }
    
    /**
     * equals
     *
     * @param name
     * @param value
     * @return
     */
    public IntegralCriteria eq(String name, Object value) {
    	super.eq(name, value);
        return this;
    }

    /**
     * not equals !=
     *
     * @param name
     * @param value
     * @return
     */
    public IntegralCriteria ne(String name, Object value) {
    	super.ne(name, value);
        return this;
    }


    /**
     * like
     *
     * @param name
     * @param value
     * @return
     */

    public IntegralCriteria like(String name, Object value) {
    	super.like(name, value);
        return this;
    }
    
    public IntegralCriteria notLike(String name, Object value) {
    	super.notLike(name, value);
        return this;
    }

    /**
     * 大于 great than
     *
     * @param name
     * @param value
     * @return
     */
    public IntegralCriteria gt(String name, Object value) {
    	super.gt(name, value);
        return this;
    }

    /**
     * 大于等于 great or equal
     *
     * @param name
     * @param value
     * @return
     */
    public IntegralCriteria ge(String name, Object value) {
    	super.ge(name, value);
        return this;
    }

    /**
     * 小于 less than
     *
     * @param name
     * @param value
     * @return
     */
    public IntegralCriteria lt(String name, Object value) {
    	super.lt(name, value);
        return this;
    }

    /**
     * 小于等于 less or equal
     *
     * @param name
     * @param value
     * @return
     */
    public IntegralCriteria le(String name, Object value) {
    	super.le(name, value);
        return this;
    }
    
    public IntegralCriteria isnull(String name) {
    	super.isnull(name);
        return this;
    } 

    public IntegralCriteria notNull(String name) {
    	super.notNull(name);
        return this;
    } 
    
    public IntegralCriteria empty(String name) {
    	super.empty(name);
        return this;
    } 
    
    public IntegralCriteria notEmpty(String name) {
    	super.notEmpty(name);
        return this;
    } 
    
    public IntegralCriteria add(Column column) {
    	super.add(column);
    	return this;
    }
    
    /**************************/
	
	public void addCriterion(String name, Object value, ConditionMode logic, String property, String typeHandler, String valueType) {
		 if (value == null) {
			 throw new RuntimeException("Value for " + property + " cannot be null");
		 }
		 add(Column.create(name, value, logic, typeHandler, valueType));
	}
   
	public void addCriterion(String name, Object value1, Object value2, ConditionMode logic, String property, String typeHandler, String valueType) {
		 if (value1 == null || value2 == null) {
			 throw new RuntimeException("Between values for " + property + " cannot be null");
		 }
		 add(Column.create(name, value1, value2, logic, typeHandler, valueType));
	}
		 
	public IntegralCriteria andIdIsNull() {
		isnull("id");
		return this;
	}
	
	public IntegralCriteria andIdIsNotNull() {
		notNull("id");
		return this;
	}
	
	public IntegralCriteria andIdIsEmpty() {
		empty("id");
		return this;
	}

	public IntegralCriteria andIdIsNotEmpty() {
		notEmpty("id");
		return this;
	}
       public IntegralCriteria andIdEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public IntegralCriteria andIdNotEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.NOT_EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public IntegralCriteria andIdGreaterThan(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.GREATER_THEN, "id", "java.lang.Integer", "Float");
          return this;
      }

      public IntegralCriteria andIdGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.GREATER_EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public IntegralCriteria andIdLessThan(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.LESS_THEN, "id", "java.lang.Integer", "Float");
          return this;
      }

      public IntegralCriteria andIdLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.LESS_EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public IntegralCriteria andIdBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("id", value1, value2, ConditionMode.BETWEEN, "id", "java.lang.Integer", "Float");
    	  return this;
      }

      public IntegralCriteria andIdNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("id", value1, value2, ConditionMode.NOT_BETWEEN, "id", "java.lang.Integer", "Float");
          return this;
      }
        
      public IntegralCriteria andIdIn(List<java.lang.Integer> values) {
          addCriterion("id", values, ConditionMode.IN, "id", "java.lang.Integer", "Float");
          return this;
      }

      public IntegralCriteria andIdNotIn(List<java.lang.Integer> values) {
          addCriterion("id", values, ConditionMode.NOT_IN, "id", "java.lang.Integer", "Float");
          return this;
      }
	public IntegralCriteria andUseridIsNull() {
		isnull("userid");
		return this;
	}
	
	public IntegralCriteria andUseridIsNotNull() {
		notNull("userid");
		return this;
	}
	
	public IntegralCriteria andUseridIsEmpty() {
		empty("userid");
		return this;
	}

	public IntegralCriteria andUseridIsNotEmpty() {
		notEmpty("userid");
		return this;
	}
       public IntegralCriteria andUseridEqualTo(java.lang.Long value) {
          addCriterion("userid", value, ConditionMode.EQUAL, "userid", "java.lang.Long", "Float");
          return this;
      }

      public IntegralCriteria andUseridNotEqualTo(java.lang.Long value) {
          addCriterion("userid", value, ConditionMode.NOT_EQUAL, "userid", "java.lang.Long", "Float");
          return this;
      }

      public IntegralCriteria andUseridGreaterThan(java.lang.Long value) {
          addCriterion("userid", value, ConditionMode.GREATER_THEN, "userid", "java.lang.Long", "Float");
          return this;
      }

      public IntegralCriteria andUseridGreaterThanOrEqualTo(java.lang.Long value) {
          addCriterion("userid", value, ConditionMode.GREATER_EQUAL, "userid", "java.lang.Long", "Float");
          return this;
      }

      public IntegralCriteria andUseridLessThan(java.lang.Long value) {
          addCriterion("userid", value, ConditionMode.LESS_THEN, "userid", "java.lang.Long", "Float");
          return this;
      }

      public IntegralCriteria andUseridLessThanOrEqualTo(java.lang.Long value) {
          addCriterion("userid", value, ConditionMode.LESS_EQUAL, "userid", "java.lang.Long", "Float");
          return this;
      }

      public IntegralCriteria andUseridBetween(java.lang.Long value1, java.lang.Long value2) {
    	  addCriterion("userid", value1, value2, ConditionMode.BETWEEN, "userid", "java.lang.Long", "Float");
    	  return this;
      }

      public IntegralCriteria andUseridNotBetween(java.lang.Long value1, java.lang.Long value2) {
          addCriterion("userid", value1, value2, ConditionMode.NOT_BETWEEN, "userid", "java.lang.Long", "Float");
          return this;
      }
        
      public IntegralCriteria andUseridIn(List<java.lang.Long> values) {
          addCriterion("userid", values, ConditionMode.IN, "userid", "java.lang.Long", "Float");
          return this;
      }

      public IntegralCriteria andUseridNotIn(List<java.lang.Long> values) {
          addCriterion("userid", values, ConditionMode.NOT_IN, "userid", "java.lang.Long", "Float");
          return this;
      }
	public IntegralCriteria andScoreIsNull() {
		isnull("score");
		return this;
	}
	
	public IntegralCriteria andScoreIsNotNull() {
		notNull("score");
		return this;
	}
	
	public IntegralCriteria andScoreIsEmpty() {
		empty("score");
		return this;
	}

	public IntegralCriteria andScoreIsNotEmpty() {
		notEmpty("score");
		return this;
	}
       public IntegralCriteria andScoreEqualTo(java.lang.Integer value) {
          addCriterion("score", value, ConditionMode.EQUAL, "score", "java.lang.Integer", "Float");
          return this;
      }

      public IntegralCriteria andScoreNotEqualTo(java.lang.Integer value) {
          addCriterion("score", value, ConditionMode.NOT_EQUAL, "score", "java.lang.Integer", "Float");
          return this;
      }

      public IntegralCriteria andScoreGreaterThan(java.lang.Integer value) {
          addCriterion("score", value, ConditionMode.GREATER_THEN, "score", "java.lang.Integer", "Float");
          return this;
      }

      public IntegralCriteria andScoreGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("score", value, ConditionMode.GREATER_EQUAL, "score", "java.lang.Integer", "Float");
          return this;
      }

      public IntegralCriteria andScoreLessThan(java.lang.Integer value) {
          addCriterion("score", value, ConditionMode.LESS_THEN, "score", "java.lang.Integer", "Float");
          return this;
      }

      public IntegralCriteria andScoreLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("score", value, ConditionMode.LESS_EQUAL, "score", "java.lang.Integer", "Float");
          return this;
      }

      public IntegralCriteria andScoreBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("score", value1, value2, ConditionMode.BETWEEN, "score", "java.lang.Integer", "Float");
    	  return this;
      }

      public IntegralCriteria andScoreNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("score", value1, value2, ConditionMode.NOT_BETWEEN, "score", "java.lang.Integer", "Float");
          return this;
      }
        
      public IntegralCriteria andScoreIn(List<java.lang.Integer> values) {
          addCriterion("score", values, ConditionMode.IN, "score", "java.lang.Integer", "Float");
          return this;
      }

      public IntegralCriteria andScoreNotIn(List<java.lang.Integer> values) {
          addCriterion("score", values, ConditionMode.NOT_IN, "score", "java.lang.Integer", "Float");
          return this;
      }
	public IntegralCriteria andTypeIsNull() {
		isnull("type");
		return this;
	}
	
	public IntegralCriteria andTypeIsNotNull() {
		notNull("type");
		return this;
	}
	
	public IntegralCriteria andTypeIsEmpty() {
		empty("type");
		return this;
	}

	public IntegralCriteria andTypeIsNotEmpty() {
		notEmpty("type");
		return this;
	}
        public IntegralCriteria andTypeLike(java.lang.String value) {
    	   addCriterion("type", value, ConditionMode.FUZZY, "type", "java.lang.String", "Float");
    	   return this;
      }

      public IntegralCriteria andTypeNotLike(java.lang.String value) {
          addCriterion("type", value, ConditionMode.NOT_FUZZY, "type", "java.lang.String", "Float");
          return this;
      }
      public IntegralCriteria andTypeEqualTo(java.lang.String value) {
          addCriterion("type", value, ConditionMode.EQUAL, "type", "java.lang.String", "String");
          return this;
      }

      public IntegralCriteria andTypeNotEqualTo(java.lang.String value) {
          addCriterion("type", value, ConditionMode.NOT_EQUAL, "type", "java.lang.String", "String");
          return this;
      }

      public IntegralCriteria andTypeGreaterThan(java.lang.String value) {
          addCriterion("type", value, ConditionMode.GREATER_THEN, "type", "java.lang.String", "String");
          return this;
      }

      public IntegralCriteria andTypeGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("type", value, ConditionMode.GREATER_EQUAL, "type", "java.lang.String", "String");
          return this;
      }

      public IntegralCriteria andTypeLessThan(java.lang.String value) {
          addCriterion("type", value, ConditionMode.LESS_THEN, "type", "java.lang.String", "String");
          return this;
      }

      public IntegralCriteria andTypeLessThanOrEqualTo(java.lang.String value) {
          addCriterion("type", value, ConditionMode.LESS_EQUAL, "type", "java.lang.String", "String");
          return this;
      }

      public IntegralCriteria andTypeBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("type", value1, value2, ConditionMode.BETWEEN, "type", "java.lang.String", "String");
    	  return this;
      }

      public IntegralCriteria andTypeNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("type", value1, value2, ConditionMode.NOT_BETWEEN, "type", "java.lang.String", "String");
          return this;
      }
        
      public IntegralCriteria andTypeIn(List<java.lang.String> values) {
          addCriterion("type", values, ConditionMode.IN, "type", "java.lang.String", "String");
          return this;
      }

      public IntegralCriteria andTypeNotIn(List<java.lang.String> values) {
          addCriterion("type", values, ConditionMode.NOT_IN, "type", "java.lang.String", "String");
          return this;
      }
	public IntegralCriteria andCreatedIsNull() {
		isnull("created");
		return this;
	}
	
	public IntegralCriteria andCreatedIsNotNull() {
		notNull("created");
		return this;
	}
	
	public IntegralCriteria andCreatedIsEmpty() {
		empty("created");
		return this;
	}

	public IntegralCriteria andCreatedIsNotEmpty() {
		notEmpty("created");
		return this;
	}
       public IntegralCriteria andCreatedEqualTo(java.util.Date value) {
          addCriterion("created", value, ConditionMode.EQUAL, "created", "java.util.Date", "String");
          return this;
      }

      public IntegralCriteria andCreatedNotEqualTo(java.util.Date value) {
          addCriterion("created", value, ConditionMode.NOT_EQUAL, "created", "java.util.Date", "String");
          return this;
      }

      public IntegralCriteria andCreatedGreaterThan(java.util.Date value) {
          addCriterion("created", value, ConditionMode.GREATER_THEN, "created", "java.util.Date", "String");
          return this;
      }

      public IntegralCriteria andCreatedGreaterThanOrEqualTo(java.util.Date value) {
          addCriterion("created", value, ConditionMode.GREATER_EQUAL, "created", "java.util.Date", "String");
          return this;
      }

      public IntegralCriteria andCreatedLessThan(java.util.Date value) {
          addCriterion("created", value, ConditionMode.LESS_THEN, "created", "java.util.Date", "String");
          return this;
      }

      public IntegralCriteria andCreatedLessThanOrEqualTo(java.util.Date value) {
          addCriterion("created", value, ConditionMode.LESS_EQUAL, "created", "java.util.Date", "String");
          return this;
      }

      public IntegralCriteria andCreatedBetween(java.util.Date value1, java.util.Date value2) {
    	  addCriterion("created", value1, value2, ConditionMode.BETWEEN, "created", "java.util.Date", "String");
    	  return this;
      }

      public IntegralCriteria andCreatedNotBetween(java.util.Date value1, java.util.Date value2) {
          addCriterion("created", value1, value2, ConditionMode.NOT_BETWEEN, "created", "java.util.Date", "String");
          return this;
      }
        
      public IntegralCriteria andCreatedIn(List<java.util.Date> values) {
          addCriterion("created", values, ConditionMode.IN, "created", "java.util.Date", "String");
          return this;
      }

      public IntegralCriteria andCreatedNotIn(List<java.util.Date> values) {
          addCriterion("created", values, ConditionMode.NOT_IN, "created", "java.util.Date", "String");
          return this;
      }
	public IntegralCriteria andOriginIsNull() {
		isnull("origin");
		return this;
	}
	
	public IntegralCriteria andOriginIsNotNull() {
		notNull("origin");
		return this;
	}
	
	public IntegralCriteria andOriginIsEmpty() {
		empty("origin");
		return this;
	}

	public IntegralCriteria andOriginIsNotEmpty() {
		notEmpty("origin");
		return this;
	}
        public IntegralCriteria andOriginLike(java.lang.String value) {
    	   addCriterion("origin", value, ConditionMode.FUZZY, "origin", "java.lang.String", "String");
    	   return this;
      }

      public IntegralCriteria andOriginNotLike(java.lang.String value) {
          addCriterion("origin", value, ConditionMode.NOT_FUZZY, "origin", "java.lang.String", "String");
          return this;
      }
      public IntegralCriteria andOriginEqualTo(java.lang.String value) {
          addCriterion("origin", value, ConditionMode.EQUAL, "origin", "java.lang.String", "String");
          return this;
      }

      public IntegralCriteria andOriginNotEqualTo(java.lang.String value) {
          addCriterion("origin", value, ConditionMode.NOT_EQUAL, "origin", "java.lang.String", "String");
          return this;
      }

      public IntegralCriteria andOriginGreaterThan(java.lang.String value) {
          addCriterion("origin", value, ConditionMode.GREATER_THEN, "origin", "java.lang.String", "String");
          return this;
      }

      public IntegralCriteria andOriginGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("origin", value, ConditionMode.GREATER_EQUAL, "origin", "java.lang.String", "String");
          return this;
      }

      public IntegralCriteria andOriginLessThan(java.lang.String value) {
          addCriterion("origin", value, ConditionMode.LESS_THEN, "origin", "java.lang.String", "String");
          return this;
      }

      public IntegralCriteria andOriginLessThanOrEqualTo(java.lang.String value) {
          addCriterion("origin", value, ConditionMode.LESS_EQUAL, "origin", "java.lang.String", "String");
          return this;
      }

      public IntegralCriteria andOriginBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("origin", value1, value2, ConditionMode.BETWEEN, "origin", "java.lang.String", "String");
    	  return this;
      }

      public IntegralCriteria andOriginNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("origin", value1, value2, ConditionMode.NOT_BETWEEN, "origin", "java.lang.String", "String");
          return this;
      }
        
      public IntegralCriteria andOriginIn(List<java.lang.String> values) {
          addCriterion("origin", values, ConditionMode.IN, "origin", "java.lang.String", "String");
          return this;
      }

      public IntegralCriteria andOriginNotIn(List<java.lang.String> values) {
          addCriterion("origin", values, ConditionMode.NOT_IN, "origin", "java.lang.String", "String");
          return this;
      }
	public IntegralCriteria andOridIsNull() {
		isnull("orid");
		return this;
	}
	
	public IntegralCriteria andOridIsNotNull() {
		notNull("orid");
		return this;
	}
	
	public IntegralCriteria andOridIsEmpty() {
		empty("orid");
		return this;
	}

	public IntegralCriteria andOridIsNotEmpty() {
		notEmpty("orid");
		return this;
	}
       public IntegralCriteria andOridEqualTo(java.lang.Integer value) {
          addCriterion("orid", value, ConditionMode.EQUAL, "orid", "java.lang.Integer", "Float");
          return this;
      }

      public IntegralCriteria andOridNotEqualTo(java.lang.Integer value) {
          addCriterion("orid", value, ConditionMode.NOT_EQUAL, "orid", "java.lang.Integer", "Float");
          return this;
      }

      public IntegralCriteria andOridGreaterThan(java.lang.Integer value) {
          addCriterion("orid", value, ConditionMode.GREATER_THEN, "orid", "java.lang.Integer", "Float");
          return this;
      }

      public IntegralCriteria andOridGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("orid", value, ConditionMode.GREATER_EQUAL, "orid", "java.lang.Integer", "Float");
          return this;
      }

      public IntegralCriteria andOridLessThan(java.lang.Integer value) {
          addCriterion("orid", value, ConditionMode.LESS_THEN, "orid", "java.lang.Integer", "Float");
          return this;
      }

      public IntegralCriteria andOridLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("orid", value, ConditionMode.LESS_EQUAL, "orid", "java.lang.Integer", "Float");
          return this;
      }

      public IntegralCriteria andOridBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("orid", value1, value2, ConditionMode.BETWEEN, "orid", "java.lang.Integer", "Float");
    	  return this;
      }

      public IntegralCriteria andOridNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("orid", value1, value2, ConditionMode.NOT_BETWEEN, "orid", "java.lang.Integer", "Float");
          return this;
      }
        
      public IntegralCriteria andOridIn(List<java.lang.Integer> values) {
          addCriterion("orid", values, ConditionMode.IN, "orid", "java.lang.Integer", "Float");
          return this;
      }

      public IntegralCriteria andOridNotIn(List<java.lang.Integer> values) {
          addCriterion("orid", values, ConditionMode.NOT_IN, "orid", "java.lang.Integer", "Float");
          return this;
      }
}