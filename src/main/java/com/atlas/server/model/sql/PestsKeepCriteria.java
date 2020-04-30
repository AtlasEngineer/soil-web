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
 * @date 2020-04-29
 * @version 1.0
 * @since 1.0
 */
public class PestsKeepCriteria extends Columns implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static PestsKeepCriteria create() {
		return new PestsKeepCriteria();
	}
	
	public static PestsKeepCriteria create(Column column) {
		PestsKeepCriteria that = new PestsKeepCriteria();
		that.add(column);
        return that;
    }

    public static PestsKeepCriteria create(String name, Object value) {
        return (PestsKeepCriteria) create().eq(name, value);
    }
    
    public Example example() {
    	return Example.create("at_pests_keep", this);
    }
    
    /**
     * equals
     *
     * @param name
     * @param value
     * @return
     */
    public PestsKeepCriteria eq(String name, Object value) {
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
    public PestsKeepCriteria ne(String name, Object value) {
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

    public PestsKeepCriteria like(String name, Object value) {
    	super.like(name, value);
        return this;
    }
    
    public PestsKeepCriteria notLike(String name, Object value) {
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
    public PestsKeepCriteria gt(String name, Object value) {
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
    public PestsKeepCriteria ge(String name, Object value) {
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
    public PestsKeepCriteria lt(String name, Object value) {
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
    public PestsKeepCriteria le(String name, Object value) {
    	super.le(name, value);
        return this;
    }
    
    public PestsKeepCriteria isnull(String name) {
    	super.isnull(name);
        return this;
    } 

    public PestsKeepCriteria notNull(String name) {
    	super.notNull(name);
        return this;
    } 
    
    public PestsKeepCriteria empty(String name) {
    	super.empty(name);
        return this;
    } 
    
    public PestsKeepCriteria notEmpty(String name) {
    	super.notEmpty(name);
        return this;
    } 
    
    public PestsKeepCriteria add(Column column) {
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
		 
	public PestsKeepCriteria andIdIsNull() {
		isnull("id");
		return this;
	}
	
	public PestsKeepCriteria andIdIsNotNull() {
		notNull("id");
		return this;
	}
	
	public PestsKeepCriteria andIdIsEmpty() {
		empty("id");
		return this;
	}

	public PestsKeepCriteria andIdIsNotEmpty() {
		notEmpty("id");
		return this;
	}
       public PestsKeepCriteria andIdEqualTo(java.lang.Long value) {
          addCriterion("id", value, ConditionMode.EQUAL, "id", "java.lang.Long", "Float");
          return this;
      }

      public PestsKeepCriteria andIdNotEqualTo(java.lang.Long value) {
          addCriterion("id", value, ConditionMode.NOT_EQUAL, "id", "java.lang.Long", "Float");
          return this;
      }

      public PestsKeepCriteria andIdGreaterThan(java.lang.Long value) {
          addCriterion("id", value, ConditionMode.GREATER_THEN, "id", "java.lang.Long", "Float");
          return this;
      }

      public PestsKeepCriteria andIdGreaterThanOrEqualTo(java.lang.Long value) {
          addCriterion("id", value, ConditionMode.GREATER_EQUAL, "id", "java.lang.Long", "Float");
          return this;
      }

      public PestsKeepCriteria andIdLessThan(java.lang.Long value) {
          addCriterion("id", value, ConditionMode.LESS_THEN, "id", "java.lang.Long", "Float");
          return this;
      }

      public PestsKeepCriteria andIdLessThanOrEqualTo(java.lang.Long value) {
          addCriterion("id", value, ConditionMode.LESS_EQUAL, "id", "java.lang.Long", "Float");
          return this;
      }

      public PestsKeepCriteria andIdBetween(java.lang.Long value1, java.lang.Long value2) {
    	  addCriterion("id", value1, value2, ConditionMode.BETWEEN, "id", "java.lang.Long", "Float");
    	  return this;
      }

      public PestsKeepCriteria andIdNotBetween(java.lang.Long value1, java.lang.Long value2) {
          addCriterion("id", value1, value2, ConditionMode.NOT_BETWEEN, "id", "java.lang.Long", "Float");
          return this;
      }
        
      public PestsKeepCriteria andIdIn(List<java.lang.Long> values) {
          addCriterion("id", values, ConditionMode.IN, "id", "java.lang.Long", "Float");
          return this;
      }

      public PestsKeepCriteria andIdNotIn(List<java.lang.Long> values) {
          addCriterion("id", values, ConditionMode.NOT_IN, "id", "java.lang.Long", "Float");
          return this;
      }
	public PestsKeepCriteria andNameIsNull() {
		isnull("name");
		return this;
	}
	
	public PestsKeepCriteria andNameIsNotNull() {
		notNull("name");
		return this;
	}
	
	public PestsKeepCriteria andNameIsEmpty() {
		empty("name");
		return this;
	}

	public PestsKeepCriteria andNameIsNotEmpty() {
		notEmpty("name");
		return this;
	}
        public PestsKeepCriteria andNameLike(java.lang.String value) {
    	   addCriterion("name", value, ConditionMode.FUZZY, "name", "java.lang.String", "Float");
    	   return this;
      }

      public PestsKeepCriteria andNameNotLike(java.lang.String value) {
          addCriterion("name", value, ConditionMode.NOT_FUZZY, "name", "java.lang.String", "Float");
          return this;
      }
      public PestsKeepCriteria andNameEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public PestsKeepCriteria andNameNotEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.NOT_EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public PestsKeepCriteria andNameGreaterThan(java.lang.String value) {
          addCriterion("name", value, ConditionMode.GREATER_THEN, "name", "java.lang.String", "String");
          return this;
      }

      public PestsKeepCriteria andNameGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.GREATER_EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public PestsKeepCriteria andNameLessThan(java.lang.String value) {
          addCriterion("name", value, ConditionMode.LESS_THEN, "name", "java.lang.String", "String");
          return this;
      }

      public PestsKeepCriteria andNameLessThanOrEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.LESS_EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public PestsKeepCriteria andNameBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("name", value1, value2, ConditionMode.BETWEEN, "name", "java.lang.String", "String");
    	  return this;
      }

      public PestsKeepCriteria andNameNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("name", value1, value2, ConditionMode.NOT_BETWEEN, "name", "java.lang.String", "String");
          return this;
      }
        
      public PestsKeepCriteria andNameIn(List<java.lang.String> values) {
          addCriterion("name", values, ConditionMode.IN, "name", "java.lang.String", "String");
          return this;
      }

      public PestsKeepCriteria andNameNotIn(List<java.lang.String> values) {
          addCriterion("name", values, ConditionMode.NOT_IN, "name", "java.lang.String", "String");
          return this;
      }
	public PestsKeepCriteria andUrlIsNull() {
		isnull("url");
		return this;
	}
	
	public PestsKeepCriteria andUrlIsNotNull() {
		notNull("url");
		return this;
	}
	
	public PestsKeepCriteria andUrlIsEmpty() {
		empty("url");
		return this;
	}

	public PestsKeepCriteria andUrlIsNotEmpty() {
		notEmpty("url");
		return this;
	}
        public PestsKeepCriteria andUrlLike(java.lang.String value) {
    	   addCriterion("url", value, ConditionMode.FUZZY, "url", "java.lang.String", "String");
    	   return this;
      }

      public PestsKeepCriteria andUrlNotLike(java.lang.String value) {
          addCriterion("url", value, ConditionMode.NOT_FUZZY, "url", "java.lang.String", "String");
          return this;
      }
      public PestsKeepCriteria andUrlEqualTo(java.lang.String value) {
          addCriterion("url", value, ConditionMode.EQUAL, "url", "java.lang.String", "String");
          return this;
      }

      public PestsKeepCriteria andUrlNotEqualTo(java.lang.String value) {
          addCriterion("url", value, ConditionMode.NOT_EQUAL, "url", "java.lang.String", "String");
          return this;
      }

      public PestsKeepCriteria andUrlGreaterThan(java.lang.String value) {
          addCriterion("url", value, ConditionMode.GREATER_THEN, "url", "java.lang.String", "String");
          return this;
      }

      public PestsKeepCriteria andUrlGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("url", value, ConditionMode.GREATER_EQUAL, "url", "java.lang.String", "String");
          return this;
      }

      public PestsKeepCriteria andUrlLessThan(java.lang.String value) {
          addCriterion("url", value, ConditionMode.LESS_THEN, "url", "java.lang.String", "String");
          return this;
      }

      public PestsKeepCriteria andUrlLessThanOrEqualTo(java.lang.String value) {
          addCriterion("url", value, ConditionMode.LESS_EQUAL, "url", "java.lang.String", "String");
          return this;
      }

      public PestsKeepCriteria andUrlBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("url", value1, value2, ConditionMode.BETWEEN, "url", "java.lang.String", "String");
    	  return this;
      }

      public PestsKeepCriteria andUrlNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("url", value1, value2, ConditionMode.NOT_BETWEEN, "url", "java.lang.String", "String");
          return this;
      }
        
      public PestsKeepCriteria andUrlIn(List<java.lang.String> values) {
          addCriterion("url", values, ConditionMode.IN, "url", "java.lang.String", "String");
          return this;
      }

      public PestsKeepCriteria andUrlNotIn(List<java.lang.String> values) {
          addCriterion("url", values, ConditionMode.NOT_IN, "url", "java.lang.String", "String");
          return this;
      }
	public PestsKeepCriteria andUserIdIsNull() {
		isnull("user_id");
		return this;
	}
	
	public PestsKeepCriteria andUserIdIsNotNull() {
		notNull("user_id");
		return this;
	}
	
	public PestsKeepCriteria andUserIdIsEmpty() {
		empty("user_id");
		return this;
	}

	public PestsKeepCriteria andUserIdIsNotEmpty() {
		notEmpty("user_id");
		return this;
	}
       public PestsKeepCriteria andUserIdEqualTo(java.lang.Integer value) {
          addCriterion("user_id", value, ConditionMode.EQUAL, "userId", "java.lang.Integer", "Float");
          return this;
      }

      public PestsKeepCriteria andUserIdNotEqualTo(java.lang.Integer value) {
          addCriterion("user_id", value, ConditionMode.NOT_EQUAL, "userId", "java.lang.Integer", "Float");
          return this;
      }

      public PestsKeepCriteria andUserIdGreaterThan(java.lang.Integer value) {
          addCriterion("user_id", value, ConditionMode.GREATER_THEN, "userId", "java.lang.Integer", "Float");
          return this;
      }

      public PestsKeepCriteria andUserIdGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("user_id", value, ConditionMode.GREATER_EQUAL, "userId", "java.lang.Integer", "Float");
          return this;
      }

      public PestsKeepCriteria andUserIdLessThan(java.lang.Integer value) {
          addCriterion("user_id", value, ConditionMode.LESS_THEN, "userId", "java.lang.Integer", "Float");
          return this;
      }

      public PestsKeepCriteria andUserIdLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("user_id", value, ConditionMode.LESS_EQUAL, "userId", "java.lang.Integer", "Float");
          return this;
      }

      public PestsKeepCriteria andUserIdBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("user_id", value1, value2, ConditionMode.BETWEEN, "userId", "java.lang.Integer", "Float");
    	  return this;
      }

      public PestsKeepCriteria andUserIdNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("user_id", value1, value2, ConditionMode.NOT_BETWEEN, "userId", "java.lang.Integer", "Float");
          return this;
      }
        
      public PestsKeepCriteria andUserIdIn(List<java.lang.Integer> values) {
          addCriterion("user_id", values, ConditionMode.IN, "userId", "java.lang.Integer", "Float");
          return this;
      }

      public PestsKeepCriteria andUserIdNotIn(List<java.lang.Integer> values) {
          addCriterion("user_id", values, ConditionMode.NOT_IN, "userId", "java.lang.Integer", "Float");
          return this;
      }
	public PestsKeepCriteria andDelIsNull() {
		isnull("del");
		return this;
	}
	
	public PestsKeepCriteria andDelIsNotNull() {
		notNull("del");
		return this;
	}
	
	public PestsKeepCriteria andDelIsEmpty() {
		empty("del");
		return this;
	}

	public PestsKeepCriteria andDelIsNotEmpty() {
		notEmpty("del");
		return this;
	}
       public PestsKeepCriteria andDelEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public PestsKeepCriteria andDelNotEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.NOT_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public PestsKeepCriteria andDelGreaterThan(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.GREATER_THEN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public PestsKeepCriteria andDelGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.GREATER_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public PestsKeepCriteria andDelLessThan(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.LESS_THEN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public PestsKeepCriteria andDelLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.LESS_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public PestsKeepCriteria andDelBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("del", value1, value2, ConditionMode.BETWEEN, "del", "java.lang.Integer", "Float");
    	  return this;
      }

      public PestsKeepCriteria andDelNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("del", value1, value2, ConditionMode.NOT_BETWEEN, "del", "java.lang.Integer", "Float");
          return this;
      }
        
      public PestsKeepCriteria andDelIn(List<java.lang.Integer> values) {
          addCriterion("del", values, ConditionMode.IN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public PestsKeepCriteria andDelNotIn(List<java.lang.Integer> values) {
          addCriterion("del", values, ConditionMode.NOT_IN, "del", "java.lang.Integer", "Float");
          return this;
      }
	public PestsKeepCriteria andPestsIdIsNull() {
		isnull("pests_id");
		return this;
	}
	
	public PestsKeepCriteria andPestsIdIsNotNull() {
		notNull("pests_id");
		return this;
	}
	
	public PestsKeepCriteria andPestsIdIsEmpty() {
		empty("pests_id");
		return this;
	}

	public PestsKeepCriteria andPestsIdIsNotEmpty() {
		notEmpty("pests_id");
		return this;
	}
        public PestsKeepCriteria andPestsIdLike(java.lang.String value) {
    	   addCriterion("pests_id", value, ConditionMode.FUZZY, "pestsId", "java.lang.String", "Float");
    	   return this;
      }

      public PestsKeepCriteria andPestsIdNotLike(java.lang.String value) {
          addCriterion("pests_id", value, ConditionMode.NOT_FUZZY, "pestsId", "java.lang.String", "Float");
          return this;
      }
      public PestsKeepCriteria andPestsIdEqualTo(java.lang.String value) {
          addCriterion("pests_id", value, ConditionMode.EQUAL, "pestsId", "java.lang.String", "String");
          return this;
      }

      public PestsKeepCriteria andPestsIdNotEqualTo(java.lang.String value) {
          addCriterion("pests_id", value, ConditionMode.NOT_EQUAL, "pestsId", "java.lang.String", "String");
          return this;
      }

      public PestsKeepCriteria andPestsIdGreaterThan(java.lang.String value) {
          addCriterion("pests_id", value, ConditionMode.GREATER_THEN, "pestsId", "java.lang.String", "String");
          return this;
      }

      public PestsKeepCriteria andPestsIdGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("pests_id", value, ConditionMode.GREATER_EQUAL, "pestsId", "java.lang.String", "String");
          return this;
      }

      public PestsKeepCriteria andPestsIdLessThan(java.lang.String value) {
          addCriterion("pests_id", value, ConditionMode.LESS_THEN, "pestsId", "java.lang.String", "String");
          return this;
      }

      public PestsKeepCriteria andPestsIdLessThanOrEqualTo(java.lang.String value) {
          addCriterion("pests_id", value, ConditionMode.LESS_EQUAL, "pestsId", "java.lang.String", "String");
          return this;
      }

      public PestsKeepCriteria andPestsIdBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("pests_id", value1, value2, ConditionMode.BETWEEN, "pestsId", "java.lang.String", "String");
    	  return this;
      }

      public PestsKeepCriteria andPestsIdNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("pests_id", value1, value2, ConditionMode.NOT_BETWEEN, "pestsId", "java.lang.String", "String");
          return this;
      }
        
      public PestsKeepCriteria andPestsIdIn(List<java.lang.String> values) {
          addCriterion("pests_id", values, ConditionMode.IN, "pestsId", "java.lang.String", "String");
          return this;
      }

      public PestsKeepCriteria andPestsIdNotIn(List<java.lang.String> values) {
          addCriterion("pests_id", values, ConditionMode.NOT_IN, "pestsId", "java.lang.String", "String");
          return this;
      }
	public PestsKeepCriteria andTimeIsNull() {
		isnull("time");
		return this;
	}
	
	public PestsKeepCriteria andTimeIsNotNull() {
		notNull("time");
		return this;
	}
	
	public PestsKeepCriteria andTimeIsEmpty() {
		empty("time");
		return this;
	}

	public PestsKeepCriteria andTimeIsNotEmpty() {
		notEmpty("time");
		return this;
	}
       public PestsKeepCriteria andTimeEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public PestsKeepCriteria andTimeNotEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.NOT_EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public PestsKeepCriteria andTimeGreaterThan(java.util.Date value) {
          addCriterion("time", value, ConditionMode.GREATER_THEN, "time", "java.util.Date", "String");
          return this;
      }

      public PestsKeepCriteria andTimeGreaterThanOrEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.GREATER_EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public PestsKeepCriteria andTimeLessThan(java.util.Date value) {
          addCriterion("time", value, ConditionMode.LESS_THEN, "time", "java.util.Date", "String");
          return this;
      }

      public PestsKeepCriteria andTimeLessThanOrEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.LESS_EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public PestsKeepCriteria andTimeBetween(java.util.Date value1, java.util.Date value2) {
    	  addCriterion("time", value1, value2, ConditionMode.BETWEEN, "time", "java.util.Date", "String");
    	  return this;
      }

      public PestsKeepCriteria andTimeNotBetween(java.util.Date value1, java.util.Date value2) {
          addCriterion("time", value1, value2, ConditionMode.NOT_BETWEEN, "time", "java.util.Date", "String");
          return this;
      }
        
      public PestsKeepCriteria andTimeIn(List<java.util.Date> values) {
          addCriterion("time", values, ConditionMode.IN, "time", "java.util.Date", "String");
          return this;
      }

      public PestsKeepCriteria andTimeNotIn(List<java.util.Date> values) {
          addCriterion("time", values, ConditionMode.NOT_IN, "time", "java.util.Date", "String");
          return this;
      }
	public PestsKeepCriteria andSampleIdIsNull() {
		isnull("sample_id");
		return this;
	}
	
	public PestsKeepCriteria andSampleIdIsNotNull() {
		notNull("sample_id");
		return this;
	}
	
	public PestsKeepCriteria andSampleIdIsEmpty() {
		empty("sample_id");
		return this;
	}

	public PestsKeepCriteria andSampleIdIsNotEmpty() {
		notEmpty("sample_id");
		return this;
	}
        public PestsKeepCriteria andSampleIdLike(java.lang.String value) {
    	   addCriterion("sample_id", value, ConditionMode.FUZZY, "sampleId", "java.lang.String", "String");
    	   return this;
      }

      public PestsKeepCriteria andSampleIdNotLike(java.lang.String value) {
          addCriterion("sample_id", value, ConditionMode.NOT_FUZZY, "sampleId", "java.lang.String", "String");
          return this;
      }
      public PestsKeepCriteria andSampleIdEqualTo(java.lang.String value) {
          addCriterion("sample_id", value, ConditionMode.EQUAL, "sampleId", "java.lang.String", "String");
          return this;
      }

      public PestsKeepCriteria andSampleIdNotEqualTo(java.lang.String value) {
          addCriterion("sample_id", value, ConditionMode.NOT_EQUAL, "sampleId", "java.lang.String", "String");
          return this;
      }

      public PestsKeepCriteria andSampleIdGreaterThan(java.lang.String value) {
          addCriterion("sample_id", value, ConditionMode.GREATER_THEN, "sampleId", "java.lang.String", "String");
          return this;
      }

      public PestsKeepCriteria andSampleIdGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("sample_id", value, ConditionMode.GREATER_EQUAL, "sampleId", "java.lang.String", "String");
          return this;
      }

      public PestsKeepCriteria andSampleIdLessThan(java.lang.String value) {
          addCriterion("sample_id", value, ConditionMode.LESS_THEN, "sampleId", "java.lang.String", "String");
          return this;
      }

      public PestsKeepCriteria andSampleIdLessThanOrEqualTo(java.lang.String value) {
          addCriterion("sample_id", value, ConditionMode.LESS_EQUAL, "sampleId", "java.lang.String", "String");
          return this;
      }

      public PestsKeepCriteria andSampleIdBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("sample_id", value1, value2, ConditionMode.BETWEEN, "sampleId", "java.lang.String", "String");
    	  return this;
      }

      public PestsKeepCriteria andSampleIdNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("sample_id", value1, value2, ConditionMode.NOT_BETWEEN, "sampleId", "java.lang.String", "String");
          return this;
      }
        
      public PestsKeepCriteria andSampleIdIn(List<java.lang.String> values) {
          addCriterion("sample_id", values, ConditionMode.IN, "sampleId", "java.lang.String", "String");
          return this;
      }

      public PestsKeepCriteria andSampleIdNotIn(List<java.lang.String> values) {
          addCriterion("sample_id", values, ConditionMode.NOT_IN, "sampleId", "java.lang.String", "String");
          return this;
      }
	public PestsKeepCriteria andStatusIsNull() {
		isnull("status");
		return this;
	}
	
	public PestsKeepCriteria andStatusIsNotNull() {
		notNull("status");
		return this;
	}
	
	public PestsKeepCriteria andStatusIsEmpty() {
		empty("status");
		return this;
	}

	public PestsKeepCriteria andStatusIsNotEmpty() {
		notEmpty("status");
		return this;
	}
       public PestsKeepCriteria andStatusEqualTo(java.lang.Integer value) {
          addCriterion("status", value, ConditionMode.EQUAL, "status", "java.lang.Integer", "Float");
          return this;
      }

      public PestsKeepCriteria andStatusNotEqualTo(java.lang.Integer value) {
          addCriterion("status", value, ConditionMode.NOT_EQUAL, "status", "java.lang.Integer", "Float");
          return this;
      }

      public PestsKeepCriteria andStatusGreaterThan(java.lang.Integer value) {
          addCriterion("status", value, ConditionMode.GREATER_THEN, "status", "java.lang.Integer", "Float");
          return this;
      }

      public PestsKeepCriteria andStatusGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("status", value, ConditionMode.GREATER_EQUAL, "status", "java.lang.Integer", "Float");
          return this;
      }

      public PestsKeepCriteria andStatusLessThan(java.lang.Integer value) {
          addCriterion("status", value, ConditionMode.LESS_THEN, "status", "java.lang.Integer", "Float");
          return this;
      }

      public PestsKeepCriteria andStatusLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("status", value, ConditionMode.LESS_EQUAL, "status", "java.lang.Integer", "Float");
          return this;
      }

      public PestsKeepCriteria andStatusBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("status", value1, value2, ConditionMode.BETWEEN, "status", "java.lang.Integer", "Float");
    	  return this;
      }

      public PestsKeepCriteria andStatusNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("status", value1, value2, ConditionMode.NOT_BETWEEN, "status", "java.lang.Integer", "Float");
          return this;
      }
        
      public PestsKeepCriteria andStatusIn(List<java.lang.Integer> values) {
          addCriterion("status", values, ConditionMode.IN, "status", "java.lang.Integer", "Float");
          return this;
      }

      public PestsKeepCriteria andStatusNotIn(List<java.lang.Integer> values) {
          addCriterion("status", values, ConditionMode.NOT_IN, "status", "java.lang.Integer", "Float");
          return this;
      }
}