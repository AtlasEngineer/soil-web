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
 * @date 2020-08-28
 * @version 1.0
 * @since 1.0
 */
public class GeolistCriteria extends Columns implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static GeolistCriteria create() {
		return new GeolistCriteria();
	}
	
	public static GeolistCriteria create(Column column) {
		GeolistCriteria that = new GeolistCriteria();
		that.add(column);
        return that;
    }

    public static GeolistCriteria create(String name, Object value) {
        return (GeolistCriteria) create().eq(name, value);
    }
    
    public Example example() {
    	return Example.create("tr_geolist", this);
    }
    
    /**
     * equals
     *
     * @param name
     * @param value
     * @return
     */
    public GeolistCriteria eq(String name, Object value) {
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
    public GeolistCriteria ne(String name, Object value) {
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

    public GeolistCriteria like(String name, Object value) {
    	super.like(name, value);
        return this;
    }
    
    public GeolistCriteria notLike(String name, Object value) {
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
    public GeolistCriteria gt(String name, Object value) {
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
    public GeolistCriteria ge(String name, Object value) {
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
    public GeolistCriteria lt(String name, Object value) {
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
    public GeolistCriteria le(String name, Object value) {
    	super.le(name, value);
        return this;
    }
    
    public GeolistCriteria isnull(String name) {
    	super.isnull(name);
        return this;
    } 

    public GeolistCriteria notNull(String name) {
    	super.notNull(name);
        return this;
    } 
    
    public GeolistCriteria empty(String name) {
    	super.empty(name);
        return this;
    } 
    
    public GeolistCriteria notEmpty(String name) {
    	super.notEmpty(name);
        return this;
    } 
    
    public GeolistCriteria add(Column column) {
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
		 
	public GeolistCriteria andIdIsNull() {
		isnull("id");
		return this;
	}
	
	public GeolistCriteria andIdIsNotNull() {
		notNull("id");
		return this;
	}
	
	public GeolistCriteria andIdIsEmpty() {
		empty("id");
		return this;
	}

	public GeolistCriteria andIdIsNotEmpty() {
		notEmpty("id");
		return this;
	}
       public GeolistCriteria andIdEqualTo(java.lang.Long value) {
          addCriterion("id", value, ConditionMode.EQUAL, "id", "java.lang.Long", "Float");
          return this;
      }

      public GeolistCriteria andIdNotEqualTo(java.lang.Long value) {
          addCriterion("id", value, ConditionMode.NOT_EQUAL, "id", "java.lang.Long", "Float");
          return this;
      }

      public GeolistCriteria andIdGreaterThan(java.lang.Long value) {
          addCriterion("id", value, ConditionMode.GREATER_THEN, "id", "java.lang.Long", "Float");
          return this;
      }

      public GeolistCriteria andIdGreaterThanOrEqualTo(java.lang.Long value) {
          addCriterion("id", value, ConditionMode.GREATER_EQUAL, "id", "java.lang.Long", "Float");
          return this;
      }

      public GeolistCriteria andIdLessThan(java.lang.Long value) {
          addCriterion("id", value, ConditionMode.LESS_THEN, "id", "java.lang.Long", "Float");
          return this;
      }

      public GeolistCriteria andIdLessThanOrEqualTo(java.lang.Long value) {
          addCriterion("id", value, ConditionMode.LESS_EQUAL, "id", "java.lang.Long", "Float");
          return this;
      }

      public GeolistCriteria andIdBetween(java.lang.Long value1, java.lang.Long value2) {
    	  addCriterion("id", value1, value2, ConditionMode.BETWEEN, "id", "java.lang.Long", "Float");
    	  return this;
      }

      public GeolistCriteria andIdNotBetween(java.lang.Long value1, java.lang.Long value2) {
          addCriterion("id", value1, value2, ConditionMode.NOT_BETWEEN, "id", "java.lang.Long", "Float");
          return this;
      }
        
      public GeolistCriteria andIdIn(List<java.lang.Long> values) {
          addCriterion("id", values, ConditionMode.IN, "id", "java.lang.Long", "Float");
          return this;
      }

      public GeolistCriteria andIdNotIn(List<java.lang.Long> values) {
          addCriterion("id", values, ConditionMode.NOT_IN, "id", "java.lang.Long", "Float");
          return this;
      }
	public GeolistCriteria andNameIsNull() {
		isnull("name");
		return this;
	}
	
	public GeolistCriteria andNameIsNotNull() {
		notNull("name");
		return this;
	}
	
	public GeolistCriteria andNameIsEmpty() {
		empty("name");
		return this;
	}

	public GeolistCriteria andNameIsNotEmpty() {
		notEmpty("name");
		return this;
	}
        public GeolistCriteria andNameLike(java.lang.String value) {
    	   addCriterion("name", value, ConditionMode.FUZZY, "name", "java.lang.String", "Float");
    	   return this;
      }

      public GeolistCriteria andNameNotLike(java.lang.String value) {
          addCriterion("name", value, ConditionMode.NOT_FUZZY, "name", "java.lang.String", "Float");
          return this;
      }
      public GeolistCriteria andNameEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public GeolistCriteria andNameNotEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.NOT_EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public GeolistCriteria andNameGreaterThan(java.lang.String value) {
          addCriterion("name", value, ConditionMode.GREATER_THEN, "name", "java.lang.String", "String");
          return this;
      }

      public GeolistCriteria andNameGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.GREATER_EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public GeolistCriteria andNameLessThan(java.lang.String value) {
          addCriterion("name", value, ConditionMode.LESS_THEN, "name", "java.lang.String", "String");
          return this;
      }

      public GeolistCriteria andNameLessThanOrEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.LESS_EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public GeolistCriteria andNameBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("name", value1, value2, ConditionMode.BETWEEN, "name", "java.lang.String", "String");
    	  return this;
      }

      public GeolistCriteria andNameNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("name", value1, value2, ConditionMode.NOT_BETWEEN, "name", "java.lang.String", "String");
          return this;
      }
        
      public GeolistCriteria andNameIn(List<java.lang.String> values) {
          addCriterion("name", values, ConditionMode.IN, "name", "java.lang.String", "String");
          return this;
      }

      public GeolistCriteria andNameNotIn(List<java.lang.String> values) {
          addCriterion("name", values, ConditionMode.NOT_IN, "name", "java.lang.String", "String");
          return this;
      }
	public GeolistCriteria andDatatypeIsNull() {
		isnull("datatype");
		return this;
	}
	
	public GeolistCriteria andDatatypeIsNotNull() {
		notNull("datatype");
		return this;
	}
	
	public GeolistCriteria andDatatypeIsEmpty() {
		empty("datatype");
		return this;
	}

	public GeolistCriteria andDatatypeIsNotEmpty() {
		notEmpty("datatype");
		return this;
	}
        public GeolistCriteria andDatatypeLike(java.lang.String value) {
    	   addCriterion("datatype", value, ConditionMode.FUZZY, "datatype", "java.lang.String", "String");
    	   return this;
      }

      public GeolistCriteria andDatatypeNotLike(java.lang.String value) {
          addCriterion("datatype", value, ConditionMode.NOT_FUZZY, "datatype", "java.lang.String", "String");
          return this;
      }
      public GeolistCriteria andDatatypeEqualTo(java.lang.String value) {
          addCriterion("datatype", value, ConditionMode.EQUAL, "datatype", "java.lang.String", "String");
          return this;
      }

      public GeolistCriteria andDatatypeNotEqualTo(java.lang.String value) {
          addCriterion("datatype", value, ConditionMode.NOT_EQUAL, "datatype", "java.lang.String", "String");
          return this;
      }

      public GeolistCriteria andDatatypeGreaterThan(java.lang.String value) {
          addCriterion("datatype", value, ConditionMode.GREATER_THEN, "datatype", "java.lang.String", "String");
          return this;
      }

      public GeolistCriteria andDatatypeGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("datatype", value, ConditionMode.GREATER_EQUAL, "datatype", "java.lang.String", "String");
          return this;
      }

      public GeolistCriteria andDatatypeLessThan(java.lang.String value) {
          addCriterion("datatype", value, ConditionMode.LESS_THEN, "datatype", "java.lang.String", "String");
          return this;
      }

      public GeolistCriteria andDatatypeLessThanOrEqualTo(java.lang.String value) {
          addCriterion("datatype", value, ConditionMode.LESS_EQUAL, "datatype", "java.lang.String", "String");
          return this;
      }

      public GeolistCriteria andDatatypeBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("datatype", value1, value2, ConditionMode.BETWEEN, "datatype", "java.lang.String", "String");
    	  return this;
      }

      public GeolistCriteria andDatatypeNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("datatype", value1, value2, ConditionMode.NOT_BETWEEN, "datatype", "java.lang.String", "String");
          return this;
      }
        
      public GeolistCriteria andDatatypeIn(List<java.lang.String> values) {
          addCriterion("datatype", values, ConditionMode.IN, "datatype", "java.lang.String", "String");
          return this;
      }

      public GeolistCriteria andDatatypeNotIn(List<java.lang.String> values) {
          addCriterion("datatype", values, ConditionMode.NOT_IN, "datatype", "java.lang.String", "String");
          return this;
      }
	public GeolistCriteria andUrlIsNull() {
		isnull("url");
		return this;
	}
	
	public GeolistCriteria andUrlIsNotNull() {
		notNull("url");
		return this;
	}
	
	public GeolistCriteria andUrlIsEmpty() {
		empty("url");
		return this;
	}

	public GeolistCriteria andUrlIsNotEmpty() {
		notEmpty("url");
		return this;
	}
        public GeolistCriteria andUrlLike(java.lang.String value) {
    	   addCriterion("url", value, ConditionMode.FUZZY, "url", "java.lang.String", "String");
    	   return this;
      }

      public GeolistCriteria andUrlNotLike(java.lang.String value) {
          addCriterion("url", value, ConditionMode.NOT_FUZZY, "url", "java.lang.String", "String");
          return this;
      }
      public GeolistCriteria andUrlEqualTo(java.lang.String value) {
          addCriterion("url", value, ConditionMode.EQUAL, "url", "java.lang.String", "String");
          return this;
      }

      public GeolistCriteria andUrlNotEqualTo(java.lang.String value) {
          addCriterion("url", value, ConditionMode.NOT_EQUAL, "url", "java.lang.String", "String");
          return this;
      }

      public GeolistCriteria andUrlGreaterThan(java.lang.String value) {
          addCriterion("url", value, ConditionMode.GREATER_THEN, "url", "java.lang.String", "String");
          return this;
      }

      public GeolistCriteria andUrlGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("url", value, ConditionMode.GREATER_EQUAL, "url", "java.lang.String", "String");
          return this;
      }

      public GeolistCriteria andUrlLessThan(java.lang.String value) {
          addCriterion("url", value, ConditionMode.LESS_THEN, "url", "java.lang.String", "String");
          return this;
      }

      public GeolistCriteria andUrlLessThanOrEqualTo(java.lang.String value) {
          addCriterion("url", value, ConditionMode.LESS_EQUAL, "url", "java.lang.String", "String");
          return this;
      }

      public GeolistCriteria andUrlBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("url", value1, value2, ConditionMode.BETWEEN, "url", "java.lang.String", "String");
    	  return this;
      }

      public GeolistCriteria andUrlNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("url", value1, value2, ConditionMode.NOT_BETWEEN, "url", "java.lang.String", "String");
          return this;
      }
        
      public GeolistCriteria andUrlIn(List<java.lang.String> values) {
          addCriterion("url", values, ConditionMode.IN, "url", "java.lang.String", "String");
          return this;
      }

      public GeolistCriteria andUrlNotIn(List<java.lang.String> values) {
          addCriterion("url", values, ConditionMode.NOT_IN, "url", "java.lang.String", "String");
          return this;
      }
	public GeolistCriteria andDtTimeIsNull() {
		isnull("dt_time");
		return this;
	}
	
	public GeolistCriteria andDtTimeIsNotNull() {
		notNull("dt_time");
		return this;
	}
	
	public GeolistCriteria andDtTimeIsEmpty() {
		empty("dt_time");
		return this;
	}

	public GeolistCriteria andDtTimeIsNotEmpty() {
		notEmpty("dt_time");
		return this;
	}
       public GeolistCriteria andDtTimeEqualTo(java.util.Date value) {
          addCriterion("dt_time", value, ConditionMode.EQUAL, "dtTime", "java.util.Date", "String");
          return this;
      }

      public GeolistCriteria andDtTimeNotEqualTo(java.util.Date value) {
          addCriterion("dt_time", value, ConditionMode.NOT_EQUAL, "dtTime", "java.util.Date", "String");
          return this;
      }

      public GeolistCriteria andDtTimeGreaterThan(java.util.Date value) {
          addCriterion("dt_time", value, ConditionMode.GREATER_THEN, "dtTime", "java.util.Date", "String");
          return this;
      }

      public GeolistCriteria andDtTimeGreaterThanOrEqualTo(java.util.Date value) {
          addCriterion("dt_time", value, ConditionMode.GREATER_EQUAL, "dtTime", "java.util.Date", "String");
          return this;
      }

      public GeolistCriteria andDtTimeLessThan(java.util.Date value) {
          addCriterion("dt_time", value, ConditionMode.LESS_THEN, "dtTime", "java.util.Date", "String");
          return this;
      }

      public GeolistCriteria andDtTimeLessThanOrEqualTo(java.util.Date value) {
          addCriterion("dt_time", value, ConditionMode.LESS_EQUAL, "dtTime", "java.util.Date", "String");
          return this;
      }

      public GeolistCriteria andDtTimeBetween(java.util.Date value1, java.util.Date value2) {
    	  addCriterion("dt_time", value1, value2, ConditionMode.BETWEEN, "dtTime", "java.util.Date", "String");
    	  return this;
      }

      public GeolistCriteria andDtTimeNotBetween(java.util.Date value1, java.util.Date value2) {
          addCriterion("dt_time", value1, value2, ConditionMode.NOT_BETWEEN, "dtTime", "java.util.Date", "String");
          return this;
      }
        
      public GeolistCriteria andDtTimeIn(List<java.util.Date> values) {
          addCriterion("dt_time", values, ConditionMode.IN, "dtTime", "java.util.Date", "String");
          return this;
      }

      public GeolistCriteria andDtTimeNotIn(List<java.util.Date> values) {
          addCriterion("dt_time", values, ConditionMode.NOT_IN, "dtTime", "java.util.Date", "String");
          return this;
      }
}