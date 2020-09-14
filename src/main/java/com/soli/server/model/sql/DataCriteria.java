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
 * @date 2020-09-11
 * @version 1.0
 * @since 1.0
 */
public class DataCriteria extends Columns implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static DataCriteria create() {
		return new DataCriteria();
	}
	
	public static DataCriteria create(Column column) {
		DataCriteria that = new DataCriteria();
		that.add(column);
        return that;
    }

    public static DataCriteria create(String name, Object value) {
        return (DataCriteria) create().eq(name, value);
    }
    
    public Example example() {
    	return Example.create("tr_data", this);
    }
    
    /**
     * equals
     *
     * @param name
     * @param value
     * @return
     */
    public DataCriteria eq(String name, Object value) {
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
    public DataCriteria ne(String name, Object value) {
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

    public DataCriteria like(String name, Object value) {
    	super.like(name, value);
        return this;
    }
    
    public DataCriteria notLike(String name, Object value) {
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
    public DataCriteria gt(String name, Object value) {
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
    public DataCriteria ge(String name, Object value) {
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
    public DataCriteria lt(String name, Object value) {
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
    public DataCriteria le(String name, Object value) {
    	super.le(name, value);
        return this;
    }
    
    public DataCriteria isnull(String name) {
    	super.isnull(name);
        return this;
    } 

    public DataCriteria notNull(String name) {
    	super.notNull(name);
        return this;
    } 
    
    public DataCriteria empty(String name) {
    	super.empty(name);
        return this;
    } 
    
    public DataCriteria notEmpty(String name) {
    	super.notEmpty(name);
        return this;
    } 
    
    public DataCriteria add(Column column) {
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
		 
	public DataCriteria andIdIsNull() {
		isnull("id");
		return this;
	}
	
	public DataCriteria andIdIsNotNull() {
		notNull("id");
		return this;
	}
	
	public DataCriteria andIdIsEmpty() {
		empty("id");
		return this;
	}

	public DataCriteria andIdIsNotEmpty() {
		notEmpty("id");
		return this;
	}
       public DataCriteria andIdEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public DataCriteria andIdNotEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.NOT_EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public DataCriteria andIdGreaterThan(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.GREATER_THEN, "id", "java.lang.Integer", "Float");
          return this;
      }

      public DataCriteria andIdGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.GREATER_EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public DataCriteria andIdLessThan(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.LESS_THEN, "id", "java.lang.Integer", "Float");
          return this;
      }

      public DataCriteria andIdLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.LESS_EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public DataCriteria andIdBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("id", value1, value2, ConditionMode.BETWEEN, "id", "java.lang.Integer", "Float");
    	  return this;
      }

      public DataCriteria andIdNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("id", value1, value2, ConditionMode.NOT_BETWEEN, "id", "java.lang.Integer", "Float");
          return this;
      }
        
      public DataCriteria andIdIn(List<java.lang.Integer> values) {
          addCriterion("id", values, ConditionMode.IN, "id", "java.lang.Integer", "Float");
          return this;
      }

      public DataCriteria andIdNotIn(List<java.lang.Integer> values) {
          addCriterion("id", values, ConditionMode.NOT_IN, "id", "java.lang.Integer", "Float");
          return this;
      }
	public DataCriteria andNameIsNull() {
		isnull("name");
		return this;
	}
	
	public DataCriteria andNameIsNotNull() {
		notNull("name");
		return this;
	}
	
	public DataCriteria andNameIsEmpty() {
		empty("name");
		return this;
	}

	public DataCriteria andNameIsNotEmpty() {
		notEmpty("name");
		return this;
	}
        public DataCriteria andNameLike(java.lang.String value) {
    	   addCriterion("name", value, ConditionMode.FUZZY, "name", "java.lang.String", "Float");
    	   return this;
      }

      public DataCriteria andNameNotLike(java.lang.String value) {
          addCriterion("name", value, ConditionMode.NOT_FUZZY, "name", "java.lang.String", "Float");
          return this;
      }
      public DataCriteria andNameEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public DataCriteria andNameNotEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.NOT_EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public DataCriteria andNameGreaterThan(java.lang.String value) {
          addCriterion("name", value, ConditionMode.GREATER_THEN, "name", "java.lang.String", "String");
          return this;
      }

      public DataCriteria andNameGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.GREATER_EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public DataCriteria andNameLessThan(java.lang.String value) {
          addCriterion("name", value, ConditionMode.LESS_THEN, "name", "java.lang.String", "String");
          return this;
      }

      public DataCriteria andNameLessThanOrEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.LESS_EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public DataCriteria andNameBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("name", value1, value2, ConditionMode.BETWEEN, "name", "java.lang.String", "String");
    	  return this;
      }

      public DataCriteria andNameNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("name", value1, value2, ConditionMode.NOT_BETWEEN, "name", "java.lang.String", "String");
          return this;
      }
        
      public DataCriteria andNameIn(List<java.lang.String> values) {
          addCriterion("name", values, ConditionMode.IN, "name", "java.lang.String", "String");
          return this;
      }

      public DataCriteria andNameNotIn(List<java.lang.String> values) {
          addCriterion("name", values, ConditionMode.NOT_IN, "name", "java.lang.String", "String");
          return this;
      }
	public DataCriteria andUrlIsNull() {
		isnull("url");
		return this;
	}
	
	public DataCriteria andUrlIsNotNull() {
		notNull("url");
		return this;
	}
	
	public DataCriteria andUrlIsEmpty() {
		empty("url");
		return this;
	}

	public DataCriteria andUrlIsNotEmpty() {
		notEmpty("url");
		return this;
	}
        public DataCriteria andUrlLike(java.lang.String value) {
    	   addCriterion("url", value, ConditionMode.FUZZY, "url", "java.lang.String", "String");
    	   return this;
      }

      public DataCriteria andUrlNotLike(java.lang.String value) {
          addCriterion("url", value, ConditionMode.NOT_FUZZY, "url", "java.lang.String", "String");
          return this;
      }
      public DataCriteria andUrlEqualTo(java.lang.String value) {
          addCriterion("url", value, ConditionMode.EQUAL, "url", "java.lang.String", "String");
          return this;
      }

      public DataCriteria andUrlNotEqualTo(java.lang.String value) {
          addCriterion("url", value, ConditionMode.NOT_EQUAL, "url", "java.lang.String", "String");
          return this;
      }

      public DataCriteria andUrlGreaterThan(java.lang.String value) {
          addCriterion("url", value, ConditionMode.GREATER_THEN, "url", "java.lang.String", "String");
          return this;
      }

      public DataCriteria andUrlGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("url", value, ConditionMode.GREATER_EQUAL, "url", "java.lang.String", "String");
          return this;
      }

      public DataCriteria andUrlLessThan(java.lang.String value) {
          addCriterion("url", value, ConditionMode.LESS_THEN, "url", "java.lang.String", "String");
          return this;
      }

      public DataCriteria andUrlLessThanOrEqualTo(java.lang.String value) {
          addCriterion("url", value, ConditionMode.LESS_EQUAL, "url", "java.lang.String", "String");
          return this;
      }

      public DataCriteria andUrlBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("url", value1, value2, ConditionMode.BETWEEN, "url", "java.lang.String", "String");
    	  return this;
      }

      public DataCriteria andUrlNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("url", value1, value2, ConditionMode.NOT_BETWEEN, "url", "java.lang.String", "String");
          return this;
      }
        
      public DataCriteria andUrlIn(List<java.lang.String> values) {
          addCriterion("url", values, ConditionMode.IN, "url", "java.lang.String", "String");
          return this;
      }

      public DataCriteria andUrlNotIn(List<java.lang.String> values) {
          addCriterion("url", values, ConditionMode.NOT_IN, "url", "java.lang.String", "String");
          return this;
      }
	public DataCriteria andTimeIsNull() {
		isnull("time");
		return this;
	}
	
	public DataCriteria andTimeIsNotNull() {
		notNull("time");
		return this;
	}
	
	public DataCriteria andTimeIsEmpty() {
		empty("time");
		return this;
	}

	public DataCriteria andTimeIsNotEmpty() {
		notEmpty("time");
		return this;
	}
       public DataCriteria andTimeEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public DataCriteria andTimeNotEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.NOT_EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public DataCriteria andTimeGreaterThan(java.util.Date value) {
          addCriterion("time", value, ConditionMode.GREATER_THEN, "time", "java.util.Date", "String");
          return this;
      }

      public DataCriteria andTimeGreaterThanOrEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.GREATER_EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public DataCriteria andTimeLessThan(java.util.Date value) {
          addCriterion("time", value, ConditionMode.LESS_THEN, "time", "java.util.Date", "String");
          return this;
      }

      public DataCriteria andTimeLessThanOrEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.LESS_EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public DataCriteria andTimeBetween(java.util.Date value1, java.util.Date value2) {
    	  addCriterion("time", value1, value2, ConditionMode.BETWEEN, "time", "java.util.Date", "String");
    	  return this;
      }

      public DataCriteria andTimeNotBetween(java.util.Date value1, java.util.Date value2) {
          addCriterion("time", value1, value2, ConditionMode.NOT_BETWEEN, "time", "java.util.Date", "String");
          return this;
      }
        
      public DataCriteria andTimeIn(List<java.util.Date> values) {
          addCriterion("time", values, ConditionMode.IN, "time", "java.util.Date", "String");
          return this;
      }

      public DataCriteria andTimeNotIn(List<java.util.Date> values) {
          addCriterion("time", values, ConditionMode.NOT_IN, "time", "java.util.Date", "String");
          return this;
      }
	public DataCriteria andUseridIsNull() {
		isnull("userid");
		return this;
	}
	
	public DataCriteria andUseridIsNotNull() {
		notNull("userid");
		return this;
	}
	
	public DataCriteria andUseridIsEmpty() {
		empty("userid");
		return this;
	}

	public DataCriteria andUseridIsNotEmpty() {
		notEmpty("userid");
		return this;
	}
       public DataCriteria andUseridEqualTo(java.lang.Integer value) {
          addCriterion("userid", value, ConditionMode.EQUAL, "userid", "java.lang.Integer", "Float");
          return this;
      }

      public DataCriteria andUseridNotEqualTo(java.lang.Integer value) {
          addCriterion("userid", value, ConditionMode.NOT_EQUAL, "userid", "java.lang.Integer", "Float");
          return this;
      }

      public DataCriteria andUseridGreaterThan(java.lang.Integer value) {
          addCriterion("userid", value, ConditionMode.GREATER_THEN, "userid", "java.lang.Integer", "Float");
          return this;
      }

      public DataCriteria andUseridGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("userid", value, ConditionMode.GREATER_EQUAL, "userid", "java.lang.Integer", "Float");
          return this;
      }

      public DataCriteria andUseridLessThan(java.lang.Integer value) {
          addCriterion("userid", value, ConditionMode.LESS_THEN, "userid", "java.lang.Integer", "Float");
          return this;
      }

      public DataCriteria andUseridLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("userid", value, ConditionMode.LESS_EQUAL, "userid", "java.lang.Integer", "Float");
          return this;
      }

      public DataCriteria andUseridBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("userid", value1, value2, ConditionMode.BETWEEN, "userid", "java.lang.Integer", "Float");
    	  return this;
      }

      public DataCriteria andUseridNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("userid", value1, value2, ConditionMode.NOT_BETWEEN, "userid", "java.lang.Integer", "Float");
          return this;
      }
        
      public DataCriteria andUseridIn(List<java.lang.Integer> values) {
          addCriterion("userid", values, ConditionMode.IN, "userid", "java.lang.Integer", "Float");
          return this;
      }

      public DataCriteria andUseridNotIn(List<java.lang.Integer> values) {
          addCriterion("userid", values, ConditionMode.NOT_IN, "userid", "java.lang.Integer", "Float");
          return this;
      }
	public DataCriteria andTypeIsNull() {
		isnull("type");
		return this;
	}
	
	public DataCriteria andTypeIsNotNull() {
		notNull("type");
		return this;
	}
	
	public DataCriteria andTypeIsEmpty() {
		empty("type");
		return this;
	}

	public DataCriteria andTypeIsNotEmpty() {
		notEmpty("type");
		return this;
	}
       public DataCriteria andTypeEqualTo(java.lang.Integer value) {
          addCriterion("type", value, ConditionMode.EQUAL, "type", "java.lang.Integer", "Float");
          return this;
      }

      public DataCriteria andTypeNotEqualTo(java.lang.Integer value) {
          addCriterion("type", value, ConditionMode.NOT_EQUAL, "type", "java.lang.Integer", "Float");
          return this;
      }

      public DataCriteria andTypeGreaterThan(java.lang.Integer value) {
          addCriterion("type", value, ConditionMode.GREATER_THEN, "type", "java.lang.Integer", "Float");
          return this;
      }

      public DataCriteria andTypeGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("type", value, ConditionMode.GREATER_EQUAL, "type", "java.lang.Integer", "Float");
          return this;
      }

      public DataCriteria andTypeLessThan(java.lang.Integer value) {
          addCriterion("type", value, ConditionMode.LESS_THEN, "type", "java.lang.Integer", "Float");
          return this;
      }

      public DataCriteria andTypeLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("type", value, ConditionMode.LESS_EQUAL, "type", "java.lang.Integer", "Float");
          return this;
      }

      public DataCriteria andTypeBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("type", value1, value2, ConditionMode.BETWEEN, "type", "java.lang.Integer", "Float");
    	  return this;
      }

      public DataCriteria andTypeNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("type", value1, value2, ConditionMode.NOT_BETWEEN, "type", "java.lang.Integer", "Float");
          return this;
      }
        
      public DataCriteria andTypeIn(List<java.lang.Integer> values) {
          addCriterion("type", values, ConditionMode.IN, "type", "java.lang.Integer", "Float");
          return this;
      }

      public DataCriteria andTypeNotIn(List<java.lang.Integer> values) {
          addCriterion("type", values, ConditionMode.NOT_IN, "type", "java.lang.Integer", "Float");
          return this;
      }
	public DataCriteria andDelIsNull() {
		isnull("del");
		return this;
	}
	
	public DataCriteria andDelIsNotNull() {
		notNull("del");
		return this;
	}
	
	public DataCriteria andDelIsEmpty() {
		empty("del");
		return this;
	}

	public DataCriteria andDelIsNotEmpty() {
		notEmpty("del");
		return this;
	}
       public DataCriteria andDelEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public DataCriteria andDelNotEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.NOT_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public DataCriteria andDelGreaterThan(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.GREATER_THEN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public DataCriteria andDelGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.GREATER_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public DataCriteria andDelLessThan(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.LESS_THEN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public DataCriteria andDelLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.LESS_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public DataCriteria andDelBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("del", value1, value2, ConditionMode.BETWEEN, "del", "java.lang.Integer", "Float");
    	  return this;
      }

      public DataCriteria andDelNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("del", value1, value2, ConditionMode.NOT_BETWEEN, "del", "java.lang.Integer", "Float");
          return this;
      }
        
      public DataCriteria andDelIn(List<java.lang.Integer> values) {
          addCriterion("del", values, ConditionMode.IN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public DataCriteria andDelNotIn(List<java.lang.Integer> values) {
          addCriterion("del", values, ConditionMode.NOT_IN, "del", "java.lang.Integer", "Float");
          return this;
      }
	public DataCriteria andDirectoryidIsNull() {
		isnull("directoryid");
		return this;
	}
	
	public DataCriteria andDirectoryidIsNotNull() {
		notNull("directoryid");
		return this;
	}
	
	public DataCriteria andDirectoryidIsEmpty() {
		empty("directoryid");
		return this;
	}

	public DataCriteria andDirectoryidIsNotEmpty() {
		notEmpty("directoryid");
		return this;
	}
       public DataCriteria andDirectoryidEqualTo(java.lang.Integer value) {
          addCriterion("directoryid", value, ConditionMode.EQUAL, "directoryid", "java.lang.Integer", "Float");
          return this;
      }

      public DataCriteria andDirectoryidNotEqualTo(java.lang.Integer value) {
          addCriterion("directoryid", value, ConditionMode.NOT_EQUAL, "directoryid", "java.lang.Integer", "Float");
          return this;
      }

      public DataCriteria andDirectoryidGreaterThan(java.lang.Integer value) {
          addCriterion("directoryid", value, ConditionMode.GREATER_THEN, "directoryid", "java.lang.Integer", "Float");
          return this;
      }

      public DataCriteria andDirectoryidGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("directoryid", value, ConditionMode.GREATER_EQUAL, "directoryid", "java.lang.Integer", "Float");
          return this;
      }

      public DataCriteria andDirectoryidLessThan(java.lang.Integer value) {
          addCriterion("directoryid", value, ConditionMode.LESS_THEN, "directoryid", "java.lang.Integer", "Float");
          return this;
      }

      public DataCriteria andDirectoryidLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("directoryid", value, ConditionMode.LESS_EQUAL, "directoryid", "java.lang.Integer", "Float");
          return this;
      }

      public DataCriteria andDirectoryidBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("directoryid", value1, value2, ConditionMode.BETWEEN, "directoryid", "java.lang.Integer", "Float");
    	  return this;
      }

      public DataCriteria andDirectoryidNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("directoryid", value1, value2, ConditionMode.NOT_BETWEEN, "directoryid", "java.lang.Integer", "Float");
          return this;
      }
        
      public DataCriteria andDirectoryidIn(List<java.lang.Integer> values) {
          addCriterion("directoryid", values, ConditionMode.IN, "directoryid", "java.lang.Integer", "Float");
          return this;
      }

      public DataCriteria andDirectoryidNotIn(List<java.lang.Integer> values) {
          addCriterion("directoryid", values, ConditionMode.NOT_IN, "directoryid", "java.lang.Integer", "Float");
          return this;
      }
}