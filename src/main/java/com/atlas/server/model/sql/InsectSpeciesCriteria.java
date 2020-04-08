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
 * @date 2020-04-08
 * @version 1.0
 * @since 1.0
 */
public class InsectSpeciesCriteria extends Columns implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static InsectSpeciesCriteria create() {
		return new InsectSpeciesCriteria();
	}
	
	public static InsectSpeciesCriteria create(Column column) {
		InsectSpeciesCriteria that = new InsectSpeciesCriteria();
		that.add(column);
        return that;
    }

    public static InsectSpeciesCriteria create(String name, Object value) {
        return (InsectSpeciesCriteria) create().eq(name, value);
    }
    
    public Example example() {
    	return Example.create("at_insect_species", this);
    }
    
    /**
     * equals
     *
     * @param name
     * @param value
     * @return
     */
    public InsectSpeciesCriteria eq(String name, Object value) {
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
    public InsectSpeciesCriteria ne(String name, Object value) {
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

    public InsectSpeciesCriteria like(String name, Object value) {
    	super.like(name, value);
        return this;
    }
    
    public InsectSpeciesCriteria notLike(String name, Object value) {
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
    public InsectSpeciesCriteria gt(String name, Object value) {
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
    public InsectSpeciesCriteria ge(String name, Object value) {
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
    public InsectSpeciesCriteria lt(String name, Object value) {
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
    public InsectSpeciesCriteria le(String name, Object value) {
    	super.le(name, value);
        return this;
    }
    
    public InsectSpeciesCriteria isnull(String name) {
    	super.isnull(name);
        return this;
    } 

    public InsectSpeciesCriteria notNull(String name) {
    	super.notNull(name);
        return this;
    } 
    
    public InsectSpeciesCriteria empty(String name) {
    	super.empty(name);
        return this;
    } 
    
    public InsectSpeciesCriteria notEmpty(String name) {
    	super.notEmpty(name);
        return this;
    } 
    
    public InsectSpeciesCriteria add(Column column) {
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
		 
	public InsectSpeciesCriteria andIdIsNull() {
		isnull("id");
		return this;
	}
	
	public InsectSpeciesCriteria andIdIsNotNull() {
		notNull("id");
		return this;
	}
	
	public InsectSpeciesCriteria andIdIsEmpty() {
		empty("id");
		return this;
	}

	public InsectSpeciesCriteria andIdIsNotEmpty() {
		notEmpty("id");
		return this;
	}
       public InsectSpeciesCriteria andIdEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public InsectSpeciesCriteria andIdNotEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.NOT_EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public InsectSpeciesCriteria andIdGreaterThan(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.GREATER_THEN, "id", "java.lang.Integer", "Float");
          return this;
      }

      public InsectSpeciesCriteria andIdGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.GREATER_EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public InsectSpeciesCriteria andIdLessThan(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.LESS_THEN, "id", "java.lang.Integer", "Float");
          return this;
      }

      public InsectSpeciesCriteria andIdLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.LESS_EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public InsectSpeciesCriteria andIdBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("id", value1, value2, ConditionMode.BETWEEN, "id", "java.lang.Integer", "Float");
    	  return this;
      }

      public InsectSpeciesCriteria andIdNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("id", value1, value2, ConditionMode.NOT_BETWEEN, "id", "java.lang.Integer", "Float");
          return this;
      }
        
      public InsectSpeciesCriteria andIdIn(List<java.lang.Integer> values) {
          addCriterion("id", values, ConditionMode.IN, "id", "java.lang.Integer", "Float");
          return this;
      }

      public InsectSpeciesCriteria andIdNotIn(List<java.lang.Integer> values) {
          addCriterion("id", values, ConditionMode.NOT_IN, "id", "java.lang.Integer", "Float");
          return this;
      }
	public InsectSpeciesCriteria andNameIsNull() {
		isnull("name");
		return this;
	}
	
	public InsectSpeciesCriteria andNameIsNotNull() {
		notNull("name");
		return this;
	}
	
	public InsectSpeciesCriteria andNameIsEmpty() {
		empty("name");
		return this;
	}

	public InsectSpeciesCriteria andNameIsNotEmpty() {
		notEmpty("name");
		return this;
	}
        public InsectSpeciesCriteria andNameLike(java.lang.String value) {
    	   addCriterion("name", value, ConditionMode.FUZZY, "name", "java.lang.String", "Float");
    	   return this;
      }

      public InsectSpeciesCriteria andNameNotLike(java.lang.String value) {
          addCriterion("name", value, ConditionMode.NOT_FUZZY, "name", "java.lang.String", "Float");
          return this;
      }
      public InsectSpeciesCriteria andNameEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public InsectSpeciesCriteria andNameNotEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.NOT_EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public InsectSpeciesCriteria andNameGreaterThan(java.lang.String value) {
          addCriterion("name", value, ConditionMode.GREATER_THEN, "name", "java.lang.String", "String");
          return this;
      }

      public InsectSpeciesCriteria andNameGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.GREATER_EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public InsectSpeciesCriteria andNameLessThan(java.lang.String value) {
          addCriterion("name", value, ConditionMode.LESS_THEN, "name", "java.lang.String", "String");
          return this;
      }

      public InsectSpeciesCriteria andNameLessThanOrEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.LESS_EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public InsectSpeciesCriteria andNameBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("name", value1, value2, ConditionMode.BETWEEN, "name", "java.lang.String", "String");
    	  return this;
      }

      public InsectSpeciesCriteria andNameNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("name", value1, value2, ConditionMode.NOT_BETWEEN, "name", "java.lang.String", "String");
          return this;
      }
        
      public InsectSpeciesCriteria andNameIn(List<java.lang.String> values) {
          addCriterion("name", values, ConditionMode.IN, "name", "java.lang.String", "String");
          return this;
      }

      public InsectSpeciesCriteria andNameNotIn(List<java.lang.String> values) {
          addCriterion("name", values, ConditionMode.NOT_IN, "name", "java.lang.String", "String");
          return this;
      }
	public InsectSpeciesCriteria andImageIsNull() {
		isnull("image");
		return this;
	}
	
	public InsectSpeciesCriteria andImageIsNotNull() {
		notNull("image");
		return this;
	}
	
	public InsectSpeciesCriteria andImageIsEmpty() {
		empty("image");
		return this;
	}

	public InsectSpeciesCriteria andImageIsNotEmpty() {
		notEmpty("image");
		return this;
	}
        public InsectSpeciesCriteria andImageLike(java.lang.String value) {
    	   addCriterion("image", value, ConditionMode.FUZZY, "image", "java.lang.String", "String");
    	   return this;
      }

      public InsectSpeciesCriteria andImageNotLike(java.lang.String value) {
          addCriterion("image", value, ConditionMode.NOT_FUZZY, "image", "java.lang.String", "String");
          return this;
      }
      public InsectSpeciesCriteria andImageEqualTo(java.lang.String value) {
          addCriterion("image", value, ConditionMode.EQUAL, "image", "java.lang.String", "String");
          return this;
      }

      public InsectSpeciesCriteria andImageNotEqualTo(java.lang.String value) {
          addCriterion("image", value, ConditionMode.NOT_EQUAL, "image", "java.lang.String", "String");
          return this;
      }

      public InsectSpeciesCriteria andImageGreaterThan(java.lang.String value) {
          addCriterion("image", value, ConditionMode.GREATER_THEN, "image", "java.lang.String", "String");
          return this;
      }

      public InsectSpeciesCriteria andImageGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("image", value, ConditionMode.GREATER_EQUAL, "image", "java.lang.String", "String");
          return this;
      }

      public InsectSpeciesCriteria andImageLessThan(java.lang.String value) {
          addCriterion("image", value, ConditionMode.LESS_THEN, "image", "java.lang.String", "String");
          return this;
      }

      public InsectSpeciesCriteria andImageLessThanOrEqualTo(java.lang.String value) {
          addCriterion("image", value, ConditionMode.LESS_EQUAL, "image", "java.lang.String", "String");
          return this;
      }

      public InsectSpeciesCriteria andImageBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("image", value1, value2, ConditionMode.BETWEEN, "image", "java.lang.String", "String");
    	  return this;
      }

      public InsectSpeciesCriteria andImageNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("image", value1, value2, ConditionMode.NOT_BETWEEN, "image", "java.lang.String", "String");
          return this;
      }
        
      public InsectSpeciesCriteria andImageIn(List<java.lang.String> values) {
          addCriterion("image", values, ConditionMode.IN, "image", "java.lang.String", "String");
          return this;
      }

      public InsectSpeciesCriteria andImageNotIn(List<java.lang.String> values) {
          addCriterion("image", values, ConditionMode.NOT_IN, "image", "java.lang.String", "String");
          return this;
      }
	public InsectSpeciesCriteria andDelIsNull() {
		isnull("del");
		return this;
	}
	
	public InsectSpeciesCriteria andDelIsNotNull() {
		notNull("del");
		return this;
	}
	
	public InsectSpeciesCriteria andDelIsEmpty() {
		empty("del");
		return this;
	}

	public InsectSpeciesCriteria andDelIsNotEmpty() {
		notEmpty("del");
		return this;
	}
       public InsectSpeciesCriteria andDelEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public InsectSpeciesCriteria andDelNotEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.NOT_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public InsectSpeciesCriteria andDelGreaterThan(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.GREATER_THEN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public InsectSpeciesCriteria andDelGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.GREATER_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public InsectSpeciesCriteria andDelLessThan(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.LESS_THEN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public InsectSpeciesCriteria andDelLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.LESS_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public InsectSpeciesCriteria andDelBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("del", value1, value2, ConditionMode.BETWEEN, "del", "java.lang.Integer", "Float");
    	  return this;
      }

      public InsectSpeciesCriteria andDelNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("del", value1, value2, ConditionMode.NOT_BETWEEN, "del", "java.lang.Integer", "Float");
          return this;
      }
        
      public InsectSpeciesCriteria andDelIn(List<java.lang.Integer> values) {
          addCriterion("del", values, ConditionMode.IN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public InsectSpeciesCriteria andDelNotIn(List<java.lang.Integer> values) {
          addCriterion("del", values, ConditionMode.NOT_IN, "del", "java.lang.Integer", "Float");
          return this;
      }
	public InsectSpeciesCriteria andTimeIsNull() {
		isnull("time");
		return this;
	}
	
	public InsectSpeciesCriteria andTimeIsNotNull() {
		notNull("time");
		return this;
	}
	
	public InsectSpeciesCriteria andTimeIsEmpty() {
		empty("time");
		return this;
	}

	public InsectSpeciesCriteria andTimeIsNotEmpty() {
		notEmpty("time");
		return this;
	}
       public InsectSpeciesCriteria andTimeEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public InsectSpeciesCriteria andTimeNotEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.NOT_EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public InsectSpeciesCriteria andTimeGreaterThan(java.util.Date value) {
          addCriterion("time", value, ConditionMode.GREATER_THEN, "time", "java.util.Date", "String");
          return this;
      }

      public InsectSpeciesCriteria andTimeGreaterThanOrEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.GREATER_EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public InsectSpeciesCriteria andTimeLessThan(java.util.Date value) {
          addCriterion("time", value, ConditionMode.LESS_THEN, "time", "java.util.Date", "String");
          return this;
      }

      public InsectSpeciesCriteria andTimeLessThanOrEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.LESS_EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public InsectSpeciesCriteria andTimeBetween(java.util.Date value1, java.util.Date value2) {
    	  addCriterion("time", value1, value2, ConditionMode.BETWEEN, "time", "java.util.Date", "String");
    	  return this;
      }

      public InsectSpeciesCriteria andTimeNotBetween(java.util.Date value1, java.util.Date value2) {
          addCriterion("time", value1, value2, ConditionMode.NOT_BETWEEN, "time", "java.util.Date", "String");
          return this;
      }
        
      public InsectSpeciesCriteria andTimeIn(List<java.util.Date> values) {
          addCriterion("time", values, ConditionMode.IN, "time", "java.util.Date", "String");
          return this;
      }

      public InsectSpeciesCriteria andTimeNotIn(List<java.util.Date> values) {
          addCriterion("time", values, ConditionMode.NOT_IN, "time", "java.util.Date", "String");
          return this;
      }
	public InsectSpeciesCriteria andTypeIdIsNull() {
		isnull("type_id");
		return this;
	}
	
	public InsectSpeciesCriteria andTypeIdIsNotNull() {
		notNull("type_id");
		return this;
	}
	
	public InsectSpeciesCriteria andTypeIdIsEmpty() {
		empty("type_id");
		return this;
	}

	public InsectSpeciesCriteria andTypeIdIsNotEmpty() {
		notEmpty("type_id");
		return this;
	}
       public InsectSpeciesCriteria andTypeIdEqualTo(java.lang.Integer value) {
          addCriterion("type_id", value, ConditionMode.EQUAL, "typeId", "java.lang.Integer", "Float");
          return this;
      }

      public InsectSpeciesCriteria andTypeIdNotEqualTo(java.lang.Integer value) {
          addCriterion("type_id", value, ConditionMode.NOT_EQUAL, "typeId", "java.lang.Integer", "Float");
          return this;
      }

      public InsectSpeciesCriteria andTypeIdGreaterThan(java.lang.Integer value) {
          addCriterion("type_id", value, ConditionMode.GREATER_THEN, "typeId", "java.lang.Integer", "Float");
          return this;
      }

      public InsectSpeciesCriteria andTypeIdGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("type_id", value, ConditionMode.GREATER_EQUAL, "typeId", "java.lang.Integer", "Float");
          return this;
      }

      public InsectSpeciesCriteria andTypeIdLessThan(java.lang.Integer value) {
          addCriterion("type_id", value, ConditionMode.LESS_THEN, "typeId", "java.lang.Integer", "Float");
          return this;
      }

      public InsectSpeciesCriteria andTypeIdLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("type_id", value, ConditionMode.LESS_EQUAL, "typeId", "java.lang.Integer", "Float");
          return this;
      }

      public InsectSpeciesCriteria andTypeIdBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("type_id", value1, value2, ConditionMode.BETWEEN, "typeId", "java.lang.Integer", "Float");
    	  return this;
      }

      public InsectSpeciesCriteria andTypeIdNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("type_id", value1, value2, ConditionMode.NOT_BETWEEN, "typeId", "java.lang.Integer", "Float");
          return this;
      }
        
      public InsectSpeciesCriteria andTypeIdIn(List<java.lang.Integer> values) {
          addCriterion("type_id", values, ConditionMode.IN, "typeId", "java.lang.Integer", "Float");
          return this;
      }

      public InsectSpeciesCriteria andTypeIdNotIn(List<java.lang.Integer> values) {
          addCriterion("type_id", values, ConditionMode.NOT_IN, "typeId", "java.lang.Integer", "Float");
          return this;
      }
}