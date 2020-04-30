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
 * @date 2020-04-30
 * @version 1.0
 * @since 1.0
 */
public class CatalogueKeepCriteria extends Columns implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static CatalogueKeepCriteria create() {
		return new CatalogueKeepCriteria();
	}
	
	public static CatalogueKeepCriteria create(Column column) {
		CatalogueKeepCriteria that = new CatalogueKeepCriteria();
		that.add(column);
        return that;
    }

    public static CatalogueKeepCriteria create(String name, Object value) {
        return (CatalogueKeepCriteria) create().eq(name, value);
    }
    
    public Example example() {
    	return Example.create("catalogue_keep", this);
    }
    
    /**
     * equals
     *
     * @param name
     * @param value
     * @return
     */
    public CatalogueKeepCriteria eq(String name, Object value) {
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
    public CatalogueKeepCriteria ne(String name, Object value) {
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

    public CatalogueKeepCriteria like(String name, Object value) {
    	super.like(name, value);
        return this;
    }
    
    public CatalogueKeepCriteria notLike(String name, Object value) {
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
    public CatalogueKeepCriteria gt(String name, Object value) {
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
    public CatalogueKeepCriteria ge(String name, Object value) {
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
    public CatalogueKeepCriteria lt(String name, Object value) {
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
    public CatalogueKeepCriteria le(String name, Object value) {
    	super.le(name, value);
        return this;
    }
    
    public CatalogueKeepCriteria isnull(String name) {
    	super.isnull(name);
        return this;
    } 

    public CatalogueKeepCriteria notNull(String name) {
    	super.notNull(name);
        return this;
    } 
    
    public CatalogueKeepCriteria empty(String name) {
    	super.empty(name);
        return this;
    } 
    
    public CatalogueKeepCriteria notEmpty(String name) {
    	super.notEmpty(name);
        return this;
    } 
    
    public CatalogueKeepCriteria add(Column column) {
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
		 
	public CatalogueKeepCriteria andIdIsNull() {
		isnull("id");
		return this;
	}
	
	public CatalogueKeepCriteria andIdIsNotNull() {
		notNull("id");
		return this;
	}
	
	public CatalogueKeepCriteria andIdIsEmpty() {
		empty("id");
		return this;
	}

	public CatalogueKeepCriteria andIdIsNotEmpty() {
		notEmpty("id");
		return this;
	}
       public CatalogueKeepCriteria andIdEqualTo(java.lang.Long value) {
          addCriterion("id", value, ConditionMode.EQUAL, "id", "java.lang.Long", "Float");
          return this;
      }

      public CatalogueKeepCriteria andIdNotEqualTo(java.lang.Long value) {
          addCriterion("id", value, ConditionMode.NOT_EQUAL, "id", "java.lang.Long", "Float");
          return this;
      }

      public CatalogueKeepCriteria andIdGreaterThan(java.lang.Long value) {
          addCriterion("id", value, ConditionMode.GREATER_THEN, "id", "java.lang.Long", "Float");
          return this;
      }

      public CatalogueKeepCriteria andIdGreaterThanOrEqualTo(java.lang.Long value) {
          addCriterion("id", value, ConditionMode.GREATER_EQUAL, "id", "java.lang.Long", "Float");
          return this;
      }

      public CatalogueKeepCriteria andIdLessThan(java.lang.Long value) {
          addCriterion("id", value, ConditionMode.LESS_THEN, "id", "java.lang.Long", "Float");
          return this;
      }

      public CatalogueKeepCriteria andIdLessThanOrEqualTo(java.lang.Long value) {
          addCriterion("id", value, ConditionMode.LESS_EQUAL, "id", "java.lang.Long", "Float");
          return this;
      }

      public CatalogueKeepCriteria andIdBetween(java.lang.Long value1, java.lang.Long value2) {
    	  addCriterion("id", value1, value2, ConditionMode.BETWEEN, "id", "java.lang.Long", "Float");
    	  return this;
      }

      public CatalogueKeepCriteria andIdNotBetween(java.lang.Long value1, java.lang.Long value2) {
          addCriterion("id", value1, value2, ConditionMode.NOT_BETWEEN, "id", "java.lang.Long", "Float");
          return this;
      }
        
      public CatalogueKeepCriteria andIdIn(List<java.lang.Long> values) {
          addCriterion("id", values, ConditionMode.IN, "id", "java.lang.Long", "Float");
          return this;
      }

      public CatalogueKeepCriteria andIdNotIn(List<java.lang.Long> values) {
          addCriterion("id", values, ConditionMode.NOT_IN, "id", "java.lang.Long", "Float");
          return this;
      }
	public CatalogueKeepCriteria andNameIsNull() {
		isnull("name");
		return this;
	}
	
	public CatalogueKeepCriteria andNameIsNotNull() {
		notNull("name");
		return this;
	}
	
	public CatalogueKeepCriteria andNameIsEmpty() {
		empty("name");
		return this;
	}

	public CatalogueKeepCriteria andNameIsNotEmpty() {
		notEmpty("name");
		return this;
	}
        public CatalogueKeepCriteria andNameLike(java.lang.String value) {
    	   addCriterion("name", value, ConditionMode.FUZZY, "name", "java.lang.String", "Float");
    	   return this;
      }

      public CatalogueKeepCriteria andNameNotLike(java.lang.String value) {
          addCriterion("name", value, ConditionMode.NOT_FUZZY, "name", "java.lang.String", "Float");
          return this;
      }
      public CatalogueKeepCriteria andNameEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public CatalogueKeepCriteria andNameNotEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.NOT_EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public CatalogueKeepCriteria andNameGreaterThan(java.lang.String value) {
          addCriterion("name", value, ConditionMode.GREATER_THEN, "name", "java.lang.String", "String");
          return this;
      }

      public CatalogueKeepCriteria andNameGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.GREATER_EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public CatalogueKeepCriteria andNameLessThan(java.lang.String value) {
          addCriterion("name", value, ConditionMode.LESS_THEN, "name", "java.lang.String", "String");
          return this;
      }

      public CatalogueKeepCriteria andNameLessThanOrEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.LESS_EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public CatalogueKeepCriteria andNameBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("name", value1, value2, ConditionMode.BETWEEN, "name", "java.lang.String", "String");
    	  return this;
      }

      public CatalogueKeepCriteria andNameNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("name", value1, value2, ConditionMode.NOT_BETWEEN, "name", "java.lang.String", "String");
          return this;
      }
        
      public CatalogueKeepCriteria andNameIn(List<java.lang.String> values) {
          addCriterion("name", values, ConditionMode.IN, "name", "java.lang.String", "String");
          return this;
      }

      public CatalogueKeepCriteria andNameNotIn(List<java.lang.String> values) {
          addCriterion("name", values, ConditionMode.NOT_IN, "name", "java.lang.String", "String");
          return this;
      }
	public CatalogueKeepCriteria andUrlIsNull() {
		isnull("url");
		return this;
	}
	
	public CatalogueKeepCriteria andUrlIsNotNull() {
		notNull("url");
		return this;
	}
	
	public CatalogueKeepCriteria andUrlIsEmpty() {
		empty("url");
		return this;
	}

	public CatalogueKeepCriteria andUrlIsNotEmpty() {
		notEmpty("url");
		return this;
	}
        public CatalogueKeepCriteria andUrlLike(java.lang.String value) {
    	   addCriterion("url", value, ConditionMode.FUZZY, "url", "java.lang.String", "String");
    	   return this;
      }

      public CatalogueKeepCriteria andUrlNotLike(java.lang.String value) {
          addCriterion("url", value, ConditionMode.NOT_FUZZY, "url", "java.lang.String", "String");
          return this;
      }
      public CatalogueKeepCriteria andUrlEqualTo(java.lang.String value) {
          addCriterion("url", value, ConditionMode.EQUAL, "url", "java.lang.String", "String");
          return this;
      }

      public CatalogueKeepCriteria andUrlNotEqualTo(java.lang.String value) {
          addCriterion("url", value, ConditionMode.NOT_EQUAL, "url", "java.lang.String", "String");
          return this;
      }

      public CatalogueKeepCriteria andUrlGreaterThan(java.lang.String value) {
          addCriterion("url", value, ConditionMode.GREATER_THEN, "url", "java.lang.String", "String");
          return this;
      }

      public CatalogueKeepCriteria andUrlGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("url", value, ConditionMode.GREATER_EQUAL, "url", "java.lang.String", "String");
          return this;
      }

      public CatalogueKeepCriteria andUrlLessThan(java.lang.String value) {
          addCriterion("url", value, ConditionMode.LESS_THEN, "url", "java.lang.String", "String");
          return this;
      }

      public CatalogueKeepCriteria andUrlLessThanOrEqualTo(java.lang.String value) {
          addCriterion("url", value, ConditionMode.LESS_EQUAL, "url", "java.lang.String", "String");
          return this;
      }

      public CatalogueKeepCriteria andUrlBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("url", value1, value2, ConditionMode.BETWEEN, "url", "java.lang.String", "String");
    	  return this;
      }

      public CatalogueKeepCriteria andUrlNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("url", value1, value2, ConditionMode.NOT_BETWEEN, "url", "java.lang.String", "String");
          return this;
      }
        
      public CatalogueKeepCriteria andUrlIn(List<java.lang.String> values) {
          addCriterion("url", values, ConditionMode.IN, "url", "java.lang.String", "String");
          return this;
      }

      public CatalogueKeepCriteria andUrlNotIn(List<java.lang.String> values) {
          addCriterion("url", values, ConditionMode.NOT_IN, "url", "java.lang.String", "String");
          return this;
      }
	public CatalogueKeepCriteria andUserIdIsNull() {
		isnull("user_id");
		return this;
	}
	
	public CatalogueKeepCriteria andUserIdIsNotNull() {
		notNull("user_id");
		return this;
	}
	
	public CatalogueKeepCriteria andUserIdIsEmpty() {
		empty("user_id");
		return this;
	}

	public CatalogueKeepCriteria andUserIdIsNotEmpty() {
		notEmpty("user_id");
		return this;
	}
       public CatalogueKeepCriteria andUserIdEqualTo(java.lang.Integer value) {
          addCriterion("user_id", value, ConditionMode.EQUAL, "userId", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueKeepCriteria andUserIdNotEqualTo(java.lang.Integer value) {
          addCriterion("user_id", value, ConditionMode.NOT_EQUAL, "userId", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueKeepCriteria andUserIdGreaterThan(java.lang.Integer value) {
          addCriterion("user_id", value, ConditionMode.GREATER_THEN, "userId", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueKeepCriteria andUserIdGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("user_id", value, ConditionMode.GREATER_EQUAL, "userId", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueKeepCriteria andUserIdLessThan(java.lang.Integer value) {
          addCriterion("user_id", value, ConditionMode.LESS_THEN, "userId", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueKeepCriteria andUserIdLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("user_id", value, ConditionMode.LESS_EQUAL, "userId", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueKeepCriteria andUserIdBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("user_id", value1, value2, ConditionMode.BETWEEN, "userId", "java.lang.Integer", "Float");
    	  return this;
      }

      public CatalogueKeepCriteria andUserIdNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("user_id", value1, value2, ConditionMode.NOT_BETWEEN, "userId", "java.lang.Integer", "Float");
          return this;
      }
        
      public CatalogueKeepCriteria andUserIdIn(List<java.lang.Integer> values) {
          addCriterion("user_id", values, ConditionMode.IN, "userId", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueKeepCriteria andUserIdNotIn(List<java.lang.Integer> values) {
          addCriterion("user_id", values, ConditionMode.NOT_IN, "userId", "java.lang.Integer", "Float");
          return this;
      }
	public CatalogueKeepCriteria andDelIsNull() {
		isnull("del");
		return this;
	}
	
	public CatalogueKeepCriteria andDelIsNotNull() {
		notNull("del");
		return this;
	}
	
	public CatalogueKeepCriteria andDelIsEmpty() {
		empty("del");
		return this;
	}

	public CatalogueKeepCriteria andDelIsNotEmpty() {
		notEmpty("del");
		return this;
	}
       public CatalogueKeepCriteria andDelEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueKeepCriteria andDelNotEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.NOT_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueKeepCriteria andDelGreaterThan(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.GREATER_THEN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueKeepCriteria andDelGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.GREATER_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueKeepCriteria andDelLessThan(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.LESS_THEN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueKeepCriteria andDelLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.LESS_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueKeepCriteria andDelBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("del", value1, value2, ConditionMode.BETWEEN, "del", "java.lang.Integer", "Float");
    	  return this;
      }

      public CatalogueKeepCriteria andDelNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("del", value1, value2, ConditionMode.NOT_BETWEEN, "del", "java.lang.Integer", "Float");
          return this;
      }
        
      public CatalogueKeepCriteria andDelIn(List<java.lang.Integer> values) {
          addCriterion("del", values, ConditionMode.IN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueKeepCriteria andDelNotIn(List<java.lang.Integer> values) {
          addCriterion("del", values, ConditionMode.NOT_IN, "del", "java.lang.Integer", "Float");
          return this;
      }
	public CatalogueKeepCriteria andTypeIsNull() {
		isnull("type");
		return this;
	}
	
	public CatalogueKeepCriteria andTypeIsNotNull() {
		notNull("type");
		return this;
	}
	
	public CatalogueKeepCriteria andTypeIsEmpty() {
		empty("type");
		return this;
	}

	public CatalogueKeepCriteria andTypeIsNotEmpty() {
		notEmpty("type");
		return this;
	}
       public CatalogueKeepCriteria andTypeEqualTo(java.lang.Integer value) {
          addCriterion("type", value, ConditionMode.EQUAL, "type", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueKeepCriteria andTypeNotEqualTo(java.lang.Integer value) {
          addCriterion("type", value, ConditionMode.NOT_EQUAL, "type", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueKeepCriteria andTypeGreaterThan(java.lang.Integer value) {
          addCriterion("type", value, ConditionMode.GREATER_THEN, "type", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueKeepCriteria andTypeGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("type", value, ConditionMode.GREATER_EQUAL, "type", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueKeepCriteria andTypeLessThan(java.lang.Integer value) {
          addCriterion("type", value, ConditionMode.LESS_THEN, "type", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueKeepCriteria andTypeLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("type", value, ConditionMode.LESS_EQUAL, "type", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueKeepCriteria andTypeBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("type", value1, value2, ConditionMode.BETWEEN, "type", "java.lang.Integer", "Float");
    	  return this;
      }

      public CatalogueKeepCriteria andTypeNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("type", value1, value2, ConditionMode.NOT_BETWEEN, "type", "java.lang.Integer", "Float");
          return this;
      }
        
      public CatalogueKeepCriteria andTypeIn(List<java.lang.Integer> values) {
          addCriterion("type", values, ConditionMode.IN, "type", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueKeepCriteria andTypeNotIn(List<java.lang.Integer> values) {
          addCriterion("type", values, ConditionMode.NOT_IN, "type", "java.lang.Integer", "Float");
          return this;
      }
	public CatalogueKeepCriteria andTimeIsNull() {
		isnull("time");
		return this;
	}
	
	public CatalogueKeepCriteria andTimeIsNotNull() {
		notNull("time");
		return this;
	}
	
	public CatalogueKeepCriteria andTimeIsEmpty() {
		empty("time");
		return this;
	}

	public CatalogueKeepCriteria andTimeIsNotEmpty() {
		notEmpty("time");
		return this;
	}
       public CatalogueKeepCriteria andTimeEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public CatalogueKeepCriteria andTimeNotEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.NOT_EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public CatalogueKeepCriteria andTimeGreaterThan(java.util.Date value) {
          addCriterion("time", value, ConditionMode.GREATER_THEN, "time", "java.util.Date", "String");
          return this;
      }

      public CatalogueKeepCriteria andTimeGreaterThanOrEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.GREATER_EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public CatalogueKeepCriteria andTimeLessThan(java.util.Date value) {
          addCriterion("time", value, ConditionMode.LESS_THEN, "time", "java.util.Date", "String");
          return this;
      }

      public CatalogueKeepCriteria andTimeLessThanOrEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.LESS_EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public CatalogueKeepCriteria andTimeBetween(java.util.Date value1, java.util.Date value2) {
    	  addCriterion("time", value1, value2, ConditionMode.BETWEEN, "time", "java.util.Date", "String");
    	  return this;
      }

      public CatalogueKeepCriteria andTimeNotBetween(java.util.Date value1, java.util.Date value2) {
          addCriterion("time", value1, value2, ConditionMode.NOT_BETWEEN, "time", "java.util.Date", "String");
          return this;
      }
        
      public CatalogueKeepCriteria andTimeIn(List<java.util.Date> values) {
          addCriterion("time", values, ConditionMode.IN, "time", "java.util.Date", "String");
          return this;
      }

      public CatalogueKeepCriteria andTimeNotIn(List<java.util.Date> values) {
          addCriterion("time", values, ConditionMode.NOT_IN, "time", "java.util.Date", "String");
          return this;
      }
	public CatalogueKeepCriteria andSampleIdIsNull() {
		isnull("sample_id");
		return this;
	}
	
	public CatalogueKeepCriteria andSampleIdIsNotNull() {
		notNull("sample_id");
		return this;
	}
	
	public CatalogueKeepCriteria andSampleIdIsEmpty() {
		empty("sample_id");
		return this;
	}

	public CatalogueKeepCriteria andSampleIdIsNotEmpty() {
		notEmpty("sample_id");
		return this;
	}
        public CatalogueKeepCriteria andSampleIdLike(java.lang.String value) {
    	   addCriterion("sample_id", value, ConditionMode.FUZZY, "sampleId", "java.lang.String", "String");
    	   return this;
      }

      public CatalogueKeepCriteria andSampleIdNotLike(java.lang.String value) {
          addCriterion("sample_id", value, ConditionMode.NOT_FUZZY, "sampleId", "java.lang.String", "String");
          return this;
      }
      public CatalogueKeepCriteria andSampleIdEqualTo(java.lang.String value) {
          addCriterion("sample_id", value, ConditionMode.EQUAL, "sampleId", "java.lang.String", "String");
          return this;
      }

      public CatalogueKeepCriteria andSampleIdNotEqualTo(java.lang.String value) {
          addCriterion("sample_id", value, ConditionMode.NOT_EQUAL, "sampleId", "java.lang.String", "String");
          return this;
      }

      public CatalogueKeepCriteria andSampleIdGreaterThan(java.lang.String value) {
          addCriterion("sample_id", value, ConditionMode.GREATER_THEN, "sampleId", "java.lang.String", "String");
          return this;
      }

      public CatalogueKeepCriteria andSampleIdGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("sample_id", value, ConditionMode.GREATER_EQUAL, "sampleId", "java.lang.String", "String");
          return this;
      }

      public CatalogueKeepCriteria andSampleIdLessThan(java.lang.String value) {
          addCriterion("sample_id", value, ConditionMode.LESS_THEN, "sampleId", "java.lang.String", "String");
          return this;
      }

      public CatalogueKeepCriteria andSampleIdLessThanOrEqualTo(java.lang.String value) {
          addCriterion("sample_id", value, ConditionMode.LESS_EQUAL, "sampleId", "java.lang.String", "String");
          return this;
      }

      public CatalogueKeepCriteria andSampleIdBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("sample_id", value1, value2, ConditionMode.BETWEEN, "sampleId", "java.lang.String", "String");
    	  return this;
      }

      public CatalogueKeepCriteria andSampleIdNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("sample_id", value1, value2, ConditionMode.NOT_BETWEEN, "sampleId", "java.lang.String", "String");
          return this;
      }
        
      public CatalogueKeepCriteria andSampleIdIn(List<java.lang.String> values) {
          addCriterion("sample_id", values, ConditionMode.IN, "sampleId", "java.lang.String", "String");
          return this;
      }

      public CatalogueKeepCriteria andSampleIdNotIn(List<java.lang.String> values) {
          addCriterion("sample_id", values, ConditionMode.NOT_IN, "sampleId", "java.lang.String", "String");
          return this;
      }
	public CatalogueKeepCriteria andStatusIsNull() {
		isnull("status");
		return this;
	}
	
	public CatalogueKeepCriteria andStatusIsNotNull() {
		notNull("status");
		return this;
	}
	
	public CatalogueKeepCriteria andStatusIsEmpty() {
		empty("status");
		return this;
	}

	public CatalogueKeepCriteria andStatusIsNotEmpty() {
		notEmpty("status");
		return this;
	}
       public CatalogueKeepCriteria andStatusEqualTo(java.lang.Integer value) {
          addCriterion("status", value, ConditionMode.EQUAL, "status", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueKeepCriteria andStatusNotEqualTo(java.lang.Integer value) {
          addCriterion("status", value, ConditionMode.NOT_EQUAL, "status", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueKeepCriteria andStatusGreaterThan(java.lang.Integer value) {
          addCriterion("status", value, ConditionMode.GREATER_THEN, "status", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueKeepCriteria andStatusGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("status", value, ConditionMode.GREATER_EQUAL, "status", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueKeepCriteria andStatusLessThan(java.lang.Integer value) {
          addCriterion("status", value, ConditionMode.LESS_THEN, "status", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueKeepCriteria andStatusLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("status", value, ConditionMode.LESS_EQUAL, "status", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueKeepCriteria andStatusBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("status", value1, value2, ConditionMode.BETWEEN, "status", "java.lang.Integer", "Float");
    	  return this;
      }

      public CatalogueKeepCriteria andStatusNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("status", value1, value2, ConditionMode.NOT_BETWEEN, "status", "java.lang.Integer", "Float");
          return this;
      }
        
      public CatalogueKeepCriteria andStatusIn(List<java.lang.Integer> values) {
          addCriterion("status", values, ConditionMode.IN, "status", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueKeepCriteria andStatusNotIn(List<java.lang.Integer> values) {
          addCriterion("status", values, ConditionMode.NOT_IN, "status", "java.lang.Integer", "Float");
          return this;
      }
}