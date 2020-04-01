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
 * @date 2020-04-01
 * @version 1.0
 * @since 1.0
 */
public class AtBotanyTypeCriteria extends Columns implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static AtBotanyTypeCriteria create() {
		return new AtBotanyTypeCriteria();
	}
	
	public static AtBotanyTypeCriteria create(Column column) {
		AtBotanyTypeCriteria that = new AtBotanyTypeCriteria();
		that.add(column);
        return that;
    }

    public static AtBotanyTypeCriteria create(String name, Object value) {
        return (AtBotanyTypeCriteria) create().eq(name, value);
    }
    
    public Example example() {
    	return Example.create("at_botany_type", this);
    }
    
    /**
     * equals
     *
     * @param name
     * @param value
     * @return
     */
    public AtBotanyTypeCriteria eq(String name, Object value) {
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
    public AtBotanyTypeCriteria ne(String name, Object value) {
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

    public AtBotanyTypeCriteria like(String name, Object value) {
    	super.like(name, value);
        return this;
    }
    
    public AtBotanyTypeCriteria notLike(String name, Object value) {
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
    public AtBotanyTypeCriteria gt(String name, Object value) {
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
    public AtBotanyTypeCriteria ge(String name, Object value) {
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
    public AtBotanyTypeCriteria lt(String name, Object value) {
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
    public AtBotanyTypeCriteria le(String name, Object value) {
    	super.le(name, value);
        return this;
    }
    
    public AtBotanyTypeCriteria isnull(String name) {
    	super.isnull(name);
        return this;
    } 

    public AtBotanyTypeCriteria notNull(String name) {
    	super.notNull(name);
        return this;
    } 
    
    public AtBotanyTypeCriteria empty(String name) {
    	super.empty(name);
        return this;
    } 
    
    public AtBotanyTypeCriteria notEmpty(String name) {
    	super.notEmpty(name);
        return this;
    } 
    
    public AtBotanyTypeCriteria add(Column column) {
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
		 
	public AtBotanyTypeCriteria andIdIsNull() {
		isnull("id");
		return this;
	}
	
	public AtBotanyTypeCriteria andIdIsNotNull() {
		notNull("id");
		return this;
	}
	
	public AtBotanyTypeCriteria andIdIsEmpty() {
		empty("id");
		return this;
	}

	public AtBotanyTypeCriteria andIdIsNotEmpty() {
		notEmpty("id");
		return this;
	}
       public AtBotanyTypeCriteria andIdEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public AtBotanyTypeCriteria andIdNotEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.NOT_EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public AtBotanyTypeCriteria andIdGreaterThan(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.GREATER_THEN, "id", "java.lang.Integer", "Float");
          return this;
      }

      public AtBotanyTypeCriteria andIdGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.GREATER_EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public AtBotanyTypeCriteria andIdLessThan(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.LESS_THEN, "id", "java.lang.Integer", "Float");
          return this;
      }

      public AtBotanyTypeCriteria andIdLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.LESS_EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public AtBotanyTypeCriteria andIdBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("id", value1, value2, ConditionMode.BETWEEN, "id", "java.lang.Integer", "Float");
    	  return this;
      }

      public AtBotanyTypeCriteria andIdNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("id", value1, value2, ConditionMode.NOT_BETWEEN, "id", "java.lang.Integer", "Float");
          return this;
      }
        
      public AtBotanyTypeCriteria andIdIn(List<java.lang.Integer> values) {
          addCriterion("id", values, ConditionMode.IN, "id", "java.lang.Integer", "Float");
          return this;
      }

      public AtBotanyTypeCriteria andIdNotIn(List<java.lang.Integer> values) {
          addCriterion("id", values, ConditionMode.NOT_IN, "id", "java.lang.Integer", "Float");
          return this;
      }
	public AtBotanyTypeCriteria andTypeIsNull() {
		isnull("type");
		return this;
	}
	
	public AtBotanyTypeCriteria andTypeIsNotNull() {
		notNull("type");
		return this;
	}
	
	public AtBotanyTypeCriteria andTypeIsEmpty() {
		empty("type");
		return this;
	}

	public AtBotanyTypeCriteria andTypeIsNotEmpty() {
		notEmpty("type");
		return this;
	}
        public AtBotanyTypeCriteria andTypeLike(java.lang.String value) {
    	   addCriterion("type", value, ConditionMode.FUZZY, "type", "java.lang.String", "Float");
    	   return this;
      }

      public AtBotanyTypeCriteria andTypeNotLike(java.lang.String value) {
          addCriterion("type", value, ConditionMode.NOT_FUZZY, "type", "java.lang.String", "Float");
          return this;
      }
      public AtBotanyTypeCriteria andTypeEqualTo(java.lang.String value) {
          addCriterion("type", value, ConditionMode.EQUAL, "type", "java.lang.String", "String");
          return this;
      }

      public AtBotanyTypeCriteria andTypeNotEqualTo(java.lang.String value) {
          addCriterion("type", value, ConditionMode.NOT_EQUAL, "type", "java.lang.String", "String");
          return this;
      }

      public AtBotanyTypeCriteria andTypeGreaterThan(java.lang.String value) {
          addCriterion("type", value, ConditionMode.GREATER_THEN, "type", "java.lang.String", "String");
          return this;
      }

      public AtBotanyTypeCriteria andTypeGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("type", value, ConditionMode.GREATER_EQUAL, "type", "java.lang.String", "String");
          return this;
      }

      public AtBotanyTypeCriteria andTypeLessThan(java.lang.String value) {
          addCriterion("type", value, ConditionMode.LESS_THEN, "type", "java.lang.String", "String");
          return this;
      }

      public AtBotanyTypeCriteria andTypeLessThanOrEqualTo(java.lang.String value) {
          addCriterion("type", value, ConditionMode.LESS_EQUAL, "type", "java.lang.String", "String");
          return this;
      }

      public AtBotanyTypeCriteria andTypeBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("type", value1, value2, ConditionMode.BETWEEN, "type", "java.lang.String", "String");
    	  return this;
      }

      public AtBotanyTypeCriteria andTypeNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("type", value1, value2, ConditionMode.NOT_BETWEEN, "type", "java.lang.String", "String");
          return this;
      }
        
      public AtBotanyTypeCriteria andTypeIn(List<java.lang.String> values) {
          addCriterion("type", values, ConditionMode.IN, "type", "java.lang.String", "String");
          return this;
      }

      public AtBotanyTypeCriteria andTypeNotIn(List<java.lang.String> values) {
          addCriterion("type", values, ConditionMode.NOT_IN, "type", "java.lang.String", "String");
          return this;
      }
	public AtBotanyTypeCriteria andDelIsNull() {
		isnull("del");
		return this;
	}
	
	public AtBotanyTypeCriteria andDelIsNotNull() {
		notNull("del");
		return this;
	}
	
	public AtBotanyTypeCriteria andDelIsEmpty() {
		empty("del");
		return this;
	}

	public AtBotanyTypeCriteria andDelIsNotEmpty() {
		notEmpty("del");
		return this;
	}
       public AtBotanyTypeCriteria andDelEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public AtBotanyTypeCriteria andDelNotEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.NOT_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public AtBotanyTypeCriteria andDelGreaterThan(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.GREATER_THEN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public AtBotanyTypeCriteria andDelGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.GREATER_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public AtBotanyTypeCriteria andDelLessThan(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.LESS_THEN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public AtBotanyTypeCriteria andDelLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.LESS_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public AtBotanyTypeCriteria andDelBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("del", value1, value2, ConditionMode.BETWEEN, "del", "java.lang.Integer", "Float");
    	  return this;
      }

      public AtBotanyTypeCriteria andDelNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("del", value1, value2, ConditionMode.NOT_BETWEEN, "del", "java.lang.Integer", "Float");
          return this;
      }
        
      public AtBotanyTypeCriteria andDelIn(List<java.lang.Integer> values) {
          addCriterion("del", values, ConditionMode.IN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public AtBotanyTypeCriteria andDelNotIn(List<java.lang.Integer> values) {
          addCriterion("del", values, ConditionMode.NOT_IN, "del", "java.lang.Integer", "Float");
          return this;
      }
}