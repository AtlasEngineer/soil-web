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
package com.soli.server.model.sql;

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
 * @date 2020-09-27
 * @version 1.0
 * @since 1.0
 */
public class DataEachCriteria extends Columns implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static DataEachCriteria create() {
		return new DataEachCriteria();
	}
	
	public static DataEachCriteria create(Column column) {
		DataEachCriteria that = new DataEachCriteria();
		that.add(column);
        return that;
    }

    public static DataEachCriteria create(String name, Object value) {
        return (DataEachCriteria) create().eq(name, value);
    }
    
    public Example example() {
    	return Example.create("tr_data_each", this);
    }
    
    /**
     * equals
     *
     * @param name
     * @param value
     * @return
     */
    public DataEachCriteria eq(String name, Object value) {
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
    public DataEachCriteria ne(String name, Object value) {
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

    public DataEachCriteria like(String name, Object value) {
    	super.like(name, value);
        return this;
    }
    
    public DataEachCriteria notLike(String name, Object value) {
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
    public DataEachCriteria gt(String name, Object value) {
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
    public DataEachCriteria ge(String name, Object value) {
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
    public DataEachCriteria lt(String name, Object value) {
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
    public DataEachCriteria le(String name, Object value) {
    	super.le(name, value);
        return this;
    }
    
    public DataEachCriteria isnull(String name) {
    	super.isnull(name);
        return this;
    } 

    public DataEachCriteria notNull(String name) {
    	super.notNull(name);
        return this;
    } 
    
    public DataEachCriteria empty(String name) {
    	super.empty(name);
        return this;
    } 
    
    public DataEachCriteria notEmpty(String name) {
    	super.notEmpty(name);
        return this;
    } 
    
    public DataEachCriteria add(Column column) {
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
		 
	public DataEachCriteria andIdIsNull() {
		isnull("id");
		return this;
	}
	
	public DataEachCriteria andIdIsNotNull() {
		notNull("id");
		return this;
	}
	
	public DataEachCriteria andIdIsEmpty() {
		empty("id");
		return this;
	}

	public DataEachCriteria andIdIsNotEmpty() {
		notEmpty("id");
		return this;
	}
       public DataEachCriteria andIdEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public DataEachCriteria andIdNotEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.NOT_EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public DataEachCriteria andIdGreaterThan(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.GREATER_THEN, "id", "java.lang.Integer", "Float");
          return this;
      }

      public DataEachCriteria andIdGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.GREATER_EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public DataEachCriteria andIdLessThan(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.LESS_THEN, "id", "java.lang.Integer", "Float");
          return this;
      }

      public DataEachCriteria andIdLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.LESS_EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public DataEachCriteria andIdBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("id", value1, value2, ConditionMode.BETWEEN, "id", "java.lang.Integer", "Float");
    	  return this;
      }

      public DataEachCriteria andIdNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("id", value1, value2, ConditionMode.NOT_BETWEEN, "id", "java.lang.Integer", "Float");
          return this;
      }
        
      public DataEachCriteria andIdIn(List<java.lang.Integer> values) {
          addCriterion("id", values, ConditionMode.IN, "id", "java.lang.Integer", "Float");
          return this;
      }

      public DataEachCriteria andIdNotIn(List<java.lang.Integer> values) {
          addCriterion("id", values, ConditionMode.NOT_IN, "id", "java.lang.Integer", "Float");
          return this;
      }
	public DataEachCriteria andNameIsNull() {
		isnull("name");
		return this;
	}
	
	public DataEachCriteria andNameIsNotNull() {
		notNull("name");
		return this;
	}
	
	public DataEachCriteria andNameIsEmpty() {
		empty("name");
		return this;
	}

	public DataEachCriteria andNameIsNotEmpty() {
		notEmpty("name");
		return this;
	}
        public DataEachCriteria andNameLike(java.lang.String value) {
    	   addCriterion("name", value, ConditionMode.FUZZY, "name", "java.lang.String", "Float");
    	   return this;
      }

      public DataEachCriteria andNameNotLike(java.lang.String value) {
          addCriterion("name", value, ConditionMode.NOT_FUZZY, "name", "java.lang.String", "Float");
          return this;
      }
      public DataEachCriteria andNameEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public DataEachCriteria andNameNotEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.NOT_EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public DataEachCriteria andNameGreaterThan(java.lang.String value) {
          addCriterion("name", value, ConditionMode.GREATER_THEN, "name", "java.lang.String", "String");
          return this;
      }

      public DataEachCriteria andNameGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.GREATER_EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public DataEachCriteria andNameLessThan(java.lang.String value) {
          addCriterion("name", value, ConditionMode.LESS_THEN, "name", "java.lang.String", "String");
          return this;
      }

      public DataEachCriteria andNameLessThanOrEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.LESS_EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public DataEachCriteria andNameBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("name", value1, value2, ConditionMode.BETWEEN, "name", "java.lang.String", "String");
    	  return this;
      }

      public DataEachCriteria andNameNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("name", value1, value2, ConditionMode.NOT_BETWEEN, "name", "java.lang.String", "String");
          return this;
      }
        
      public DataEachCriteria andNameIn(List<java.lang.String> values) {
          addCriterion("name", values, ConditionMode.IN, "name", "java.lang.String", "String");
          return this;
      }

      public DataEachCriteria andNameNotIn(List<java.lang.String> values) {
          addCriterion("name", values, ConditionMode.NOT_IN, "name", "java.lang.String", "String");
          return this;
      }
	public DataEachCriteria andDataTimeIsNull() {
		isnull("data_time");
		return this;
	}
	
	public DataEachCriteria andDataTimeIsNotNull() {
		notNull("data_time");
		return this;
	}
	
	public DataEachCriteria andDataTimeIsEmpty() {
		empty("data_time");
		return this;
	}

	public DataEachCriteria andDataTimeIsNotEmpty() {
		notEmpty("data_time");
		return this;
	}
       public DataEachCriteria andDataTimeEqualTo(java.util.Date value) {
          addCriterion("data_time", value, ConditionMode.EQUAL, "dataTime", "java.util.Date", "String");
          return this;
      }

      public DataEachCriteria andDataTimeNotEqualTo(java.util.Date value) {
          addCriterion("data_time", value, ConditionMode.NOT_EQUAL, "dataTime", "java.util.Date", "String");
          return this;
      }

      public DataEachCriteria andDataTimeGreaterThan(java.util.Date value) {
          addCriterion("data_time", value, ConditionMode.GREATER_THEN, "dataTime", "java.util.Date", "String");
          return this;
      }

      public DataEachCriteria andDataTimeGreaterThanOrEqualTo(java.util.Date value) {
          addCriterion("data_time", value, ConditionMode.GREATER_EQUAL, "dataTime", "java.util.Date", "String");
          return this;
      }

      public DataEachCriteria andDataTimeLessThan(java.util.Date value) {
          addCriterion("data_time", value, ConditionMode.LESS_THEN, "dataTime", "java.util.Date", "String");
          return this;
      }

      public DataEachCriteria andDataTimeLessThanOrEqualTo(java.util.Date value) {
          addCriterion("data_time", value, ConditionMode.LESS_EQUAL, "dataTime", "java.util.Date", "String");
          return this;
      }

      public DataEachCriteria andDataTimeBetween(java.util.Date value1, java.util.Date value2) {
    	  addCriterion("data_time", value1, value2, ConditionMode.BETWEEN, "dataTime", "java.util.Date", "String");
    	  return this;
      }

      public DataEachCriteria andDataTimeNotBetween(java.util.Date value1, java.util.Date value2) {
          addCriterion("data_time", value1, value2, ConditionMode.NOT_BETWEEN, "dataTime", "java.util.Date", "String");
          return this;
      }
        
      public DataEachCriteria andDataTimeIn(List<java.util.Date> values) {
          addCriterion("data_time", values, ConditionMode.IN, "dataTime", "java.util.Date", "String");
          return this;
      }

      public DataEachCriteria andDataTimeNotIn(List<java.util.Date> values) {
          addCriterion("data_time", values, ConditionMode.NOT_IN, "dataTime", "java.util.Date", "String");
          return this;
      }
	public DataEachCriteria andTimeIsNull() {
		isnull("time");
		return this;
	}
	
	public DataEachCriteria andTimeIsNotNull() {
		notNull("time");
		return this;
	}
	
	public DataEachCriteria andTimeIsEmpty() {
		empty("time");
		return this;
	}

	public DataEachCriteria andTimeIsNotEmpty() {
		notEmpty("time");
		return this;
	}
       public DataEachCriteria andTimeEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public DataEachCriteria andTimeNotEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.NOT_EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public DataEachCriteria andTimeGreaterThan(java.util.Date value) {
          addCriterion("time", value, ConditionMode.GREATER_THEN, "time", "java.util.Date", "String");
          return this;
      }

      public DataEachCriteria andTimeGreaterThanOrEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.GREATER_EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public DataEachCriteria andTimeLessThan(java.util.Date value) {
          addCriterion("time", value, ConditionMode.LESS_THEN, "time", "java.util.Date", "String");
          return this;
      }

      public DataEachCriteria andTimeLessThanOrEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.LESS_EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public DataEachCriteria andTimeBetween(java.util.Date value1, java.util.Date value2) {
    	  addCriterion("time", value1, value2, ConditionMode.BETWEEN, "time", "java.util.Date", "String");
    	  return this;
      }

      public DataEachCriteria andTimeNotBetween(java.util.Date value1, java.util.Date value2) {
          addCriterion("time", value1, value2, ConditionMode.NOT_BETWEEN, "time", "java.util.Date", "String");
          return this;
      }
        
      public DataEachCriteria andTimeIn(List<java.util.Date> values) {
          addCriterion("time", values, ConditionMode.IN, "time", "java.util.Date", "String");
          return this;
      }

      public DataEachCriteria andTimeNotIn(List<java.util.Date> values) {
          addCriterion("time", values, ConditionMode.NOT_IN, "time", "java.util.Date", "String");
          return this;
      }
	public DataEachCriteria andUseridIsNull() {
		isnull("userid");
		return this;
	}
	
	public DataEachCriteria andUseridIsNotNull() {
		notNull("userid");
		return this;
	}
	
	public DataEachCriteria andUseridIsEmpty() {
		empty("userid");
		return this;
	}

	public DataEachCriteria andUseridIsNotEmpty() {
		notEmpty("userid");
		return this;
	}
       public DataEachCriteria andUseridEqualTo(java.lang.Integer value) {
          addCriterion("userid", value, ConditionMode.EQUAL, "userid", "java.lang.Integer", "Float");
          return this;
      }

      public DataEachCriteria andUseridNotEqualTo(java.lang.Integer value) {
          addCriterion("userid", value, ConditionMode.NOT_EQUAL, "userid", "java.lang.Integer", "Float");
          return this;
      }

      public DataEachCriteria andUseridGreaterThan(java.lang.Integer value) {
          addCriterion("userid", value, ConditionMode.GREATER_THEN, "userid", "java.lang.Integer", "Float");
          return this;
      }

      public DataEachCriteria andUseridGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("userid", value, ConditionMode.GREATER_EQUAL, "userid", "java.lang.Integer", "Float");
          return this;
      }

      public DataEachCriteria andUseridLessThan(java.lang.Integer value) {
          addCriterion("userid", value, ConditionMode.LESS_THEN, "userid", "java.lang.Integer", "Float");
          return this;
      }

      public DataEachCriteria andUseridLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("userid", value, ConditionMode.LESS_EQUAL, "userid", "java.lang.Integer", "Float");
          return this;
      }

      public DataEachCriteria andUseridBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("userid", value1, value2, ConditionMode.BETWEEN, "userid", "java.lang.Integer", "Float");
    	  return this;
      }

      public DataEachCriteria andUseridNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("userid", value1, value2, ConditionMode.NOT_BETWEEN, "userid", "java.lang.Integer", "Float");
          return this;
      }
        
      public DataEachCriteria andUseridIn(List<java.lang.Integer> values) {
          addCriterion("userid", values, ConditionMode.IN, "userid", "java.lang.Integer", "Float");
          return this;
      }

      public DataEachCriteria andUseridNotIn(List<java.lang.Integer> values) {
          addCriterion("userid", values, ConditionMode.NOT_IN, "userid", "java.lang.Integer", "Float");
          return this;
      }
	public DataEachCriteria andUrlIsNull() {
		isnull("url");
		return this;
	}
	
	public DataEachCriteria andUrlIsNotNull() {
		notNull("url");
		return this;
	}
	
	public DataEachCriteria andUrlIsEmpty() {
		empty("url");
		return this;
	}

	public DataEachCriteria andUrlIsNotEmpty() {
		notEmpty("url");
		return this;
	}
        public DataEachCriteria andUrlLike(java.lang.String value) {
    	   addCriterion("url", value, ConditionMode.FUZZY, "url", "java.lang.String", "Float");
    	   return this;
      }

      public DataEachCriteria andUrlNotLike(java.lang.String value) {
          addCriterion("url", value, ConditionMode.NOT_FUZZY, "url", "java.lang.String", "Float");
          return this;
      }
      public DataEachCriteria andUrlEqualTo(java.lang.String value) {
          addCriterion("url", value, ConditionMode.EQUAL, "url", "java.lang.String", "String");
          return this;
      }

      public DataEachCriteria andUrlNotEqualTo(java.lang.String value) {
          addCriterion("url", value, ConditionMode.NOT_EQUAL, "url", "java.lang.String", "String");
          return this;
      }

      public DataEachCriteria andUrlGreaterThan(java.lang.String value) {
          addCriterion("url", value, ConditionMode.GREATER_THEN, "url", "java.lang.String", "String");
          return this;
      }

      public DataEachCriteria andUrlGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("url", value, ConditionMode.GREATER_EQUAL, "url", "java.lang.String", "String");
          return this;
      }

      public DataEachCriteria andUrlLessThan(java.lang.String value) {
          addCriterion("url", value, ConditionMode.LESS_THEN, "url", "java.lang.String", "String");
          return this;
      }

      public DataEachCriteria andUrlLessThanOrEqualTo(java.lang.String value) {
          addCriterion("url", value, ConditionMode.LESS_EQUAL, "url", "java.lang.String", "String");
          return this;
      }

      public DataEachCriteria andUrlBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("url", value1, value2, ConditionMode.BETWEEN, "url", "java.lang.String", "String");
    	  return this;
      }

      public DataEachCriteria andUrlNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("url", value1, value2, ConditionMode.NOT_BETWEEN, "url", "java.lang.String", "String");
          return this;
      }
        
      public DataEachCriteria andUrlIn(List<java.lang.String> values) {
          addCriterion("url", values, ConditionMode.IN, "url", "java.lang.String", "String");
          return this;
      }

      public DataEachCriteria andUrlNotIn(List<java.lang.String> values) {
          addCriterion("url", values, ConditionMode.NOT_IN, "url", "java.lang.String", "String");
          return this;
      }
	public DataEachCriteria andTypeIsNull() {
		isnull("type");
		return this;
	}
	
	public DataEachCriteria andTypeIsNotNull() {
		notNull("type");
		return this;
	}
	
	public DataEachCriteria andTypeIsEmpty() {
		empty("type");
		return this;
	}

	public DataEachCriteria andTypeIsNotEmpty() {
		notEmpty("type");
		return this;
	}
        public DataEachCriteria andTypeLike(java.lang.String value) {
    	   addCriterion("type", value, ConditionMode.FUZZY, "type", "java.lang.String", "String");
    	   return this;
      }

      public DataEachCriteria andTypeNotLike(java.lang.String value) {
          addCriterion("type", value, ConditionMode.NOT_FUZZY, "type", "java.lang.String", "String");
          return this;
      }
      public DataEachCriteria andTypeEqualTo(java.lang.String value) {
          addCriterion("type", value, ConditionMode.EQUAL, "type", "java.lang.String", "String");
          return this;
      }

      public DataEachCriteria andTypeNotEqualTo(java.lang.String value) {
          addCriterion("type", value, ConditionMode.NOT_EQUAL, "type", "java.lang.String", "String");
          return this;
      }

      public DataEachCriteria andTypeGreaterThan(java.lang.String value) {
          addCriterion("type", value, ConditionMode.GREATER_THEN, "type", "java.lang.String", "String");
          return this;
      }

      public DataEachCriteria andTypeGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("type", value, ConditionMode.GREATER_EQUAL, "type", "java.lang.String", "String");
          return this;
      }

      public DataEachCriteria andTypeLessThan(java.lang.String value) {
          addCriterion("type", value, ConditionMode.LESS_THEN, "type", "java.lang.String", "String");
          return this;
      }

      public DataEachCriteria andTypeLessThanOrEqualTo(java.lang.String value) {
          addCriterion("type", value, ConditionMode.LESS_EQUAL, "type", "java.lang.String", "String");
          return this;
      }

      public DataEachCriteria andTypeBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("type", value1, value2, ConditionMode.BETWEEN, "type", "java.lang.String", "String");
    	  return this;
      }

      public DataEachCriteria andTypeNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("type", value1, value2, ConditionMode.NOT_BETWEEN, "type", "java.lang.String", "String");
          return this;
      }
        
      public DataEachCriteria andTypeIn(List<java.lang.String> values) {
          addCriterion("type", values, ConditionMode.IN, "type", "java.lang.String", "String");
          return this;
      }

      public DataEachCriteria andTypeNotIn(List<java.lang.String> values) {
          addCriterion("type", values, ConditionMode.NOT_IN, "type", "java.lang.String", "String");
          return this;
      }
}