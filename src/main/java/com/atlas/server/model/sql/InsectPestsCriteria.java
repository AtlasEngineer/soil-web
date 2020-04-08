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
public class InsectPestsCriteria extends Columns implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static InsectPestsCriteria create() {
		return new InsectPestsCriteria();
	}
	
	public static InsectPestsCriteria create(Column column) {
		InsectPestsCriteria that = new InsectPestsCriteria();
		that.add(column);
        return that;
    }

    public static InsectPestsCriteria create(String name, Object value) {
        return (InsectPestsCriteria) create().eq(name, value);
    }
    
    public Example example() {
    	return Example.create("at_insect_pests", this);
    }
    
    /**
     * equals
     *
     * @param name
     * @param value
     * @return
     */
    public InsectPestsCriteria eq(String name, Object value) {
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
    public InsectPestsCriteria ne(String name, Object value) {
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

    public InsectPestsCriteria like(String name, Object value) {
    	super.like(name, value);
        return this;
    }
    
    public InsectPestsCriteria notLike(String name, Object value) {
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
    public InsectPestsCriteria gt(String name, Object value) {
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
    public InsectPestsCriteria ge(String name, Object value) {
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
    public InsectPestsCriteria lt(String name, Object value) {
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
    public InsectPestsCriteria le(String name, Object value) {
    	super.le(name, value);
        return this;
    }
    
    public InsectPestsCriteria isnull(String name) {
    	super.isnull(name);
        return this;
    } 

    public InsectPestsCriteria notNull(String name) {
    	super.notNull(name);
        return this;
    } 
    
    public InsectPestsCriteria empty(String name) {
    	super.empty(name);
        return this;
    } 
    
    public InsectPestsCriteria notEmpty(String name) {
    	super.notEmpty(name);
        return this;
    } 
    
    public InsectPestsCriteria add(Column column) {
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
		 
	public InsectPestsCriteria andIdIsNull() {
		isnull("id");
		return this;
	}
	
	public InsectPestsCriteria andIdIsNotNull() {
		notNull("id");
		return this;
	}
	
	public InsectPestsCriteria andIdIsEmpty() {
		empty("id");
		return this;
	}

	public InsectPestsCriteria andIdIsNotEmpty() {
		notEmpty("id");
		return this;
	}
       public InsectPestsCriteria andIdEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public InsectPestsCriteria andIdNotEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.NOT_EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public InsectPestsCriteria andIdGreaterThan(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.GREATER_THEN, "id", "java.lang.Integer", "Float");
          return this;
      }

      public InsectPestsCriteria andIdGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.GREATER_EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public InsectPestsCriteria andIdLessThan(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.LESS_THEN, "id", "java.lang.Integer", "Float");
          return this;
      }

      public InsectPestsCriteria andIdLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.LESS_EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public InsectPestsCriteria andIdBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("id", value1, value2, ConditionMode.BETWEEN, "id", "java.lang.Integer", "Float");
    	  return this;
      }

      public InsectPestsCriteria andIdNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("id", value1, value2, ConditionMode.NOT_BETWEEN, "id", "java.lang.Integer", "Float");
          return this;
      }
        
      public InsectPestsCriteria andIdIn(List<java.lang.Integer> values) {
          addCriterion("id", values, ConditionMode.IN, "id", "java.lang.Integer", "Float");
          return this;
      }

      public InsectPestsCriteria andIdNotIn(List<java.lang.Integer> values) {
          addCriterion("id", values, ConditionMode.NOT_IN, "id", "java.lang.Integer", "Float");
          return this;
      }
	public InsectPestsCriteria andNameIsNull() {
		isnull("name");
		return this;
	}
	
	public InsectPestsCriteria andNameIsNotNull() {
		notNull("name");
		return this;
	}
	
	public InsectPestsCriteria andNameIsEmpty() {
		empty("name");
		return this;
	}

	public InsectPestsCriteria andNameIsNotEmpty() {
		notEmpty("name");
		return this;
	}
        public InsectPestsCriteria andNameLike(java.lang.String value) {
    	   addCriterion("name", value, ConditionMode.FUZZY, "name", "java.lang.String", "Float");
    	   return this;
      }

      public InsectPestsCriteria andNameNotLike(java.lang.String value) {
          addCriterion("name", value, ConditionMode.NOT_FUZZY, "name", "java.lang.String", "Float");
          return this;
      }
      public InsectPestsCriteria andNameEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public InsectPestsCriteria andNameNotEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.NOT_EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public InsectPestsCriteria andNameGreaterThan(java.lang.String value) {
          addCriterion("name", value, ConditionMode.GREATER_THEN, "name", "java.lang.String", "String");
          return this;
      }

      public InsectPestsCriteria andNameGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.GREATER_EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public InsectPestsCriteria andNameLessThan(java.lang.String value) {
          addCriterion("name", value, ConditionMode.LESS_THEN, "name", "java.lang.String", "String");
          return this;
      }

      public InsectPestsCriteria andNameLessThanOrEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.LESS_EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public InsectPestsCriteria andNameBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("name", value1, value2, ConditionMode.BETWEEN, "name", "java.lang.String", "String");
    	  return this;
      }

      public InsectPestsCriteria andNameNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("name", value1, value2, ConditionMode.NOT_BETWEEN, "name", "java.lang.String", "String");
          return this;
      }
        
      public InsectPestsCriteria andNameIn(List<java.lang.String> values) {
          addCriterion("name", values, ConditionMode.IN, "name", "java.lang.String", "String");
          return this;
      }

      public InsectPestsCriteria andNameNotIn(List<java.lang.String> values) {
          addCriterion("name", values, ConditionMode.NOT_IN, "name", "java.lang.String", "String");
          return this;
      }
	public InsectPestsCriteria andFeaturesIsNull() {
		isnull("features");
		return this;
	}
	
	public InsectPestsCriteria andFeaturesIsNotNull() {
		notNull("features");
		return this;
	}
	
	public InsectPestsCriteria andFeaturesIsEmpty() {
		empty("features");
		return this;
	}

	public InsectPestsCriteria andFeaturesIsNotEmpty() {
		notEmpty("features");
		return this;
	}
        public InsectPestsCriteria andFeaturesLike(java.lang.String value) {
    	   addCriterion("features", value, ConditionMode.FUZZY, "features", "java.lang.String", "String");
    	   return this;
      }

      public InsectPestsCriteria andFeaturesNotLike(java.lang.String value) {
          addCriterion("features", value, ConditionMode.NOT_FUZZY, "features", "java.lang.String", "String");
          return this;
      }
      public InsectPestsCriteria andFeaturesEqualTo(java.lang.String value) {
          addCriterion("features", value, ConditionMode.EQUAL, "features", "java.lang.String", "String");
          return this;
      }

      public InsectPestsCriteria andFeaturesNotEqualTo(java.lang.String value) {
          addCriterion("features", value, ConditionMode.NOT_EQUAL, "features", "java.lang.String", "String");
          return this;
      }

      public InsectPestsCriteria andFeaturesGreaterThan(java.lang.String value) {
          addCriterion("features", value, ConditionMode.GREATER_THEN, "features", "java.lang.String", "String");
          return this;
      }

      public InsectPestsCriteria andFeaturesGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("features", value, ConditionMode.GREATER_EQUAL, "features", "java.lang.String", "String");
          return this;
      }

      public InsectPestsCriteria andFeaturesLessThan(java.lang.String value) {
          addCriterion("features", value, ConditionMode.LESS_THEN, "features", "java.lang.String", "String");
          return this;
      }

      public InsectPestsCriteria andFeaturesLessThanOrEqualTo(java.lang.String value) {
          addCriterion("features", value, ConditionMode.LESS_EQUAL, "features", "java.lang.String", "String");
          return this;
      }

      public InsectPestsCriteria andFeaturesBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("features", value1, value2, ConditionMode.BETWEEN, "features", "java.lang.String", "String");
    	  return this;
      }

      public InsectPestsCriteria andFeaturesNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("features", value1, value2, ConditionMode.NOT_BETWEEN, "features", "java.lang.String", "String");
          return this;
      }
        
      public InsectPestsCriteria andFeaturesIn(List<java.lang.String> values) {
          addCriterion("features", values, ConditionMode.IN, "features", "java.lang.String", "String");
          return this;
      }

      public InsectPestsCriteria andFeaturesNotIn(List<java.lang.String> values) {
          addCriterion("features", values, ConditionMode.NOT_IN, "features", "java.lang.String", "String");
          return this;
      }
	public InsectPestsCriteria andHostIsNull() {
		isnull("host");
		return this;
	}
	
	public InsectPestsCriteria andHostIsNotNull() {
		notNull("host");
		return this;
	}
	
	public InsectPestsCriteria andHostIsEmpty() {
		empty("host");
		return this;
	}

	public InsectPestsCriteria andHostIsNotEmpty() {
		notEmpty("host");
		return this;
	}
        public InsectPestsCriteria andHostLike(java.lang.String value) {
    	   addCriterion("host", value, ConditionMode.FUZZY, "host", "java.lang.String", "String");
    	   return this;
      }

      public InsectPestsCriteria andHostNotLike(java.lang.String value) {
          addCriterion("host", value, ConditionMode.NOT_FUZZY, "host", "java.lang.String", "String");
          return this;
      }
      public InsectPestsCriteria andHostEqualTo(java.lang.String value) {
          addCriterion("host", value, ConditionMode.EQUAL, "host", "java.lang.String", "String");
          return this;
      }

      public InsectPestsCriteria andHostNotEqualTo(java.lang.String value) {
          addCriterion("host", value, ConditionMode.NOT_EQUAL, "host", "java.lang.String", "String");
          return this;
      }

      public InsectPestsCriteria andHostGreaterThan(java.lang.String value) {
          addCriterion("host", value, ConditionMode.GREATER_THEN, "host", "java.lang.String", "String");
          return this;
      }

      public InsectPestsCriteria andHostGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("host", value, ConditionMode.GREATER_EQUAL, "host", "java.lang.String", "String");
          return this;
      }

      public InsectPestsCriteria andHostLessThan(java.lang.String value) {
          addCriterion("host", value, ConditionMode.LESS_THEN, "host", "java.lang.String", "String");
          return this;
      }

      public InsectPestsCriteria andHostLessThanOrEqualTo(java.lang.String value) {
          addCriterion("host", value, ConditionMode.LESS_EQUAL, "host", "java.lang.String", "String");
          return this;
      }

      public InsectPestsCriteria andHostBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("host", value1, value2, ConditionMode.BETWEEN, "host", "java.lang.String", "String");
    	  return this;
      }

      public InsectPestsCriteria andHostNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("host", value1, value2, ConditionMode.NOT_BETWEEN, "host", "java.lang.String", "String");
          return this;
      }
        
      public InsectPestsCriteria andHostIn(List<java.lang.String> values) {
          addCriterion("host", values, ConditionMode.IN, "host", "java.lang.String", "String");
          return this;
      }

      public InsectPestsCriteria andHostNotIn(List<java.lang.String> values) {
          addCriterion("host", values, ConditionMode.NOT_IN, "host", "java.lang.String", "String");
          return this;
      }
	public InsectPestsCriteria andAddressIsNull() {
		isnull("address");
		return this;
	}
	
	public InsectPestsCriteria andAddressIsNotNull() {
		notNull("address");
		return this;
	}
	
	public InsectPestsCriteria andAddressIsEmpty() {
		empty("address");
		return this;
	}

	public InsectPestsCriteria andAddressIsNotEmpty() {
		notEmpty("address");
		return this;
	}
        public InsectPestsCriteria andAddressLike(java.lang.String value) {
    	   addCriterion("address", value, ConditionMode.FUZZY, "address", "java.lang.String", "String");
    	   return this;
      }

      public InsectPestsCriteria andAddressNotLike(java.lang.String value) {
          addCriterion("address", value, ConditionMode.NOT_FUZZY, "address", "java.lang.String", "String");
          return this;
      }
      public InsectPestsCriteria andAddressEqualTo(java.lang.String value) {
          addCriterion("address", value, ConditionMode.EQUAL, "address", "java.lang.String", "String");
          return this;
      }

      public InsectPestsCriteria andAddressNotEqualTo(java.lang.String value) {
          addCriterion("address", value, ConditionMode.NOT_EQUAL, "address", "java.lang.String", "String");
          return this;
      }

      public InsectPestsCriteria andAddressGreaterThan(java.lang.String value) {
          addCriterion("address", value, ConditionMode.GREATER_THEN, "address", "java.lang.String", "String");
          return this;
      }

      public InsectPestsCriteria andAddressGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("address", value, ConditionMode.GREATER_EQUAL, "address", "java.lang.String", "String");
          return this;
      }

      public InsectPestsCriteria andAddressLessThan(java.lang.String value) {
          addCriterion("address", value, ConditionMode.LESS_THEN, "address", "java.lang.String", "String");
          return this;
      }

      public InsectPestsCriteria andAddressLessThanOrEqualTo(java.lang.String value) {
          addCriterion("address", value, ConditionMode.LESS_EQUAL, "address", "java.lang.String", "String");
          return this;
      }

      public InsectPestsCriteria andAddressBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("address", value1, value2, ConditionMode.BETWEEN, "address", "java.lang.String", "String");
    	  return this;
      }

      public InsectPestsCriteria andAddressNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("address", value1, value2, ConditionMode.NOT_BETWEEN, "address", "java.lang.String", "String");
          return this;
      }
        
      public InsectPestsCriteria andAddressIn(List<java.lang.String> values) {
          addCriterion("address", values, ConditionMode.IN, "address", "java.lang.String", "String");
          return this;
      }

      public InsectPestsCriteria andAddressNotIn(List<java.lang.String> values) {
          addCriterion("address", values, ConditionMode.NOT_IN, "address", "java.lang.String", "String");
          return this;
      }
	public InsectPestsCriteria andHarmIsNull() {
		isnull("harm");
		return this;
	}
	
	public InsectPestsCriteria andHarmIsNotNull() {
		notNull("harm");
		return this;
	}
	
	public InsectPestsCriteria andHarmIsEmpty() {
		empty("harm");
		return this;
	}

	public InsectPestsCriteria andHarmIsNotEmpty() {
		notEmpty("harm");
		return this;
	}
        public InsectPestsCriteria andHarmLike(java.lang.String value) {
    	   addCriterion("harm", value, ConditionMode.FUZZY, "harm", "java.lang.String", "String");
    	   return this;
      }

      public InsectPestsCriteria andHarmNotLike(java.lang.String value) {
          addCriterion("harm", value, ConditionMode.NOT_FUZZY, "harm", "java.lang.String", "String");
          return this;
      }
      public InsectPestsCriteria andHarmEqualTo(java.lang.String value) {
          addCriterion("harm", value, ConditionMode.EQUAL, "harm", "java.lang.String", "String");
          return this;
      }

      public InsectPestsCriteria andHarmNotEqualTo(java.lang.String value) {
          addCriterion("harm", value, ConditionMode.NOT_EQUAL, "harm", "java.lang.String", "String");
          return this;
      }

      public InsectPestsCriteria andHarmGreaterThan(java.lang.String value) {
          addCriterion("harm", value, ConditionMode.GREATER_THEN, "harm", "java.lang.String", "String");
          return this;
      }

      public InsectPestsCriteria andHarmGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("harm", value, ConditionMode.GREATER_EQUAL, "harm", "java.lang.String", "String");
          return this;
      }

      public InsectPestsCriteria andHarmLessThan(java.lang.String value) {
          addCriterion("harm", value, ConditionMode.LESS_THEN, "harm", "java.lang.String", "String");
          return this;
      }

      public InsectPestsCriteria andHarmLessThanOrEqualTo(java.lang.String value) {
          addCriterion("harm", value, ConditionMode.LESS_EQUAL, "harm", "java.lang.String", "String");
          return this;
      }

      public InsectPestsCriteria andHarmBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("harm", value1, value2, ConditionMode.BETWEEN, "harm", "java.lang.String", "String");
    	  return this;
      }

      public InsectPestsCriteria andHarmNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("harm", value1, value2, ConditionMode.NOT_BETWEEN, "harm", "java.lang.String", "String");
          return this;
      }
        
      public InsectPestsCriteria andHarmIn(List<java.lang.String> values) {
          addCriterion("harm", values, ConditionMode.IN, "harm", "java.lang.String", "String");
          return this;
      }

      public InsectPestsCriteria andHarmNotIn(List<java.lang.String> values) {
          addCriterion("harm", values, ConditionMode.NOT_IN, "harm", "java.lang.String", "String");
          return this;
      }
	public InsectPestsCriteria andMethodIsNull() {
		isnull("method");
		return this;
	}
	
	public InsectPestsCriteria andMethodIsNotNull() {
		notNull("method");
		return this;
	}
	
	public InsectPestsCriteria andMethodIsEmpty() {
		empty("method");
		return this;
	}

	public InsectPestsCriteria andMethodIsNotEmpty() {
		notEmpty("method");
		return this;
	}
        public InsectPestsCriteria andMethodLike(java.lang.String value) {
    	   addCriterion("method", value, ConditionMode.FUZZY, "method", "java.lang.String", "String");
    	   return this;
      }

      public InsectPestsCriteria andMethodNotLike(java.lang.String value) {
          addCriterion("method", value, ConditionMode.NOT_FUZZY, "method", "java.lang.String", "String");
          return this;
      }
      public InsectPestsCriteria andMethodEqualTo(java.lang.String value) {
          addCriterion("method", value, ConditionMode.EQUAL, "method", "java.lang.String", "String");
          return this;
      }

      public InsectPestsCriteria andMethodNotEqualTo(java.lang.String value) {
          addCriterion("method", value, ConditionMode.NOT_EQUAL, "method", "java.lang.String", "String");
          return this;
      }

      public InsectPestsCriteria andMethodGreaterThan(java.lang.String value) {
          addCriterion("method", value, ConditionMode.GREATER_THEN, "method", "java.lang.String", "String");
          return this;
      }

      public InsectPestsCriteria andMethodGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("method", value, ConditionMode.GREATER_EQUAL, "method", "java.lang.String", "String");
          return this;
      }

      public InsectPestsCriteria andMethodLessThan(java.lang.String value) {
          addCriterion("method", value, ConditionMode.LESS_THEN, "method", "java.lang.String", "String");
          return this;
      }

      public InsectPestsCriteria andMethodLessThanOrEqualTo(java.lang.String value) {
          addCriterion("method", value, ConditionMode.LESS_EQUAL, "method", "java.lang.String", "String");
          return this;
      }

      public InsectPestsCriteria andMethodBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("method", value1, value2, ConditionMode.BETWEEN, "method", "java.lang.String", "String");
    	  return this;
      }

      public InsectPestsCriteria andMethodNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("method", value1, value2, ConditionMode.NOT_BETWEEN, "method", "java.lang.String", "String");
          return this;
      }
        
      public InsectPestsCriteria andMethodIn(List<java.lang.String> values) {
          addCriterion("method", values, ConditionMode.IN, "method", "java.lang.String", "String");
          return this;
      }

      public InsectPestsCriteria andMethodNotIn(List<java.lang.String> values) {
          addCriterion("method", values, ConditionMode.NOT_IN, "method", "java.lang.String", "String");
          return this;
      }
	public InsectPestsCriteria andTypeIsNull() {
		isnull("type");
		return this;
	}
	
	public InsectPestsCriteria andTypeIsNotNull() {
		notNull("type");
		return this;
	}
	
	public InsectPestsCriteria andTypeIsEmpty() {
		empty("type");
		return this;
	}

	public InsectPestsCriteria andTypeIsNotEmpty() {
		notEmpty("type");
		return this;
	}
       public InsectPestsCriteria andTypeEqualTo(java.lang.Integer value) {
          addCriterion("type", value, ConditionMode.EQUAL, "type", "java.lang.Integer", "Float");
          return this;
      }

      public InsectPestsCriteria andTypeNotEqualTo(java.lang.Integer value) {
          addCriterion("type", value, ConditionMode.NOT_EQUAL, "type", "java.lang.Integer", "Float");
          return this;
      }

      public InsectPestsCriteria andTypeGreaterThan(java.lang.Integer value) {
          addCriterion("type", value, ConditionMode.GREATER_THEN, "type", "java.lang.Integer", "Float");
          return this;
      }

      public InsectPestsCriteria andTypeGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("type", value, ConditionMode.GREATER_EQUAL, "type", "java.lang.Integer", "Float");
          return this;
      }

      public InsectPestsCriteria andTypeLessThan(java.lang.Integer value) {
          addCriterion("type", value, ConditionMode.LESS_THEN, "type", "java.lang.Integer", "Float");
          return this;
      }

      public InsectPestsCriteria andTypeLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("type", value, ConditionMode.LESS_EQUAL, "type", "java.lang.Integer", "Float");
          return this;
      }

      public InsectPestsCriteria andTypeBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("type", value1, value2, ConditionMode.BETWEEN, "type", "java.lang.Integer", "Float");
    	  return this;
      }

      public InsectPestsCriteria andTypeNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("type", value1, value2, ConditionMode.NOT_BETWEEN, "type", "java.lang.Integer", "Float");
          return this;
      }
        
      public InsectPestsCriteria andTypeIn(List<java.lang.Integer> values) {
          addCriterion("type", values, ConditionMode.IN, "type", "java.lang.Integer", "Float");
          return this;
      }

      public InsectPestsCriteria andTypeNotIn(List<java.lang.Integer> values) {
          addCriterion("type", values, ConditionMode.NOT_IN, "type", "java.lang.Integer", "Float");
          return this;
      }
	public InsectPestsCriteria andDelIsNull() {
		isnull("del");
		return this;
	}
	
	public InsectPestsCriteria andDelIsNotNull() {
		notNull("del");
		return this;
	}
	
	public InsectPestsCriteria andDelIsEmpty() {
		empty("del");
		return this;
	}

	public InsectPestsCriteria andDelIsNotEmpty() {
		notEmpty("del");
		return this;
	}
       public InsectPestsCriteria andDelEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public InsectPestsCriteria andDelNotEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.NOT_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public InsectPestsCriteria andDelGreaterThan(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.GREATER_THEN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public InsectPestsCriteria andDelGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.GREATER_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public InsectPestsCriteria andDelLessThan(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.LESS_THEN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public InsectPestsCriteria andDelLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.LESS_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public InsectPestsCriteria andDelBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("del", value1, value2, ConditionMode.BETWEEN, "del", "java.lang.Integer", "Float");
    	  return this;
      }

      public InsectPestsCriteria andDelNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("del", value1, value2, ConditionMode.NOT_BETWEEN, "del", "java.lang.Integer", "Float");
          return this;
      }
        
      public InsectPestsCriteria andDelIn(List<java.lang.Integer> values) {
          addCriterion("del", values, ConditionMode.IN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public InsectPestsCriteria andDelNotIn(List<java.lang.Integer> values) {
          addCriterion("del", values, ConditionMode.NOT_IN, "del", "java.lang.Integer", "Float");
          return this;
      }
	public InsectPestsCriteria andTimeIsNull() {
		isnull("time");
		return this;
	}
	
	public InsectPestsCriteria andTimeIsNotNull() {
		notNull("time");
		return this;
	}
	
	public InsectPestsCriteria andTimeIsEmpty() {
		empty("time");
		return this;
	}

	public InsectPestsCriteria andTimeIsNotEmpty() {
		notEmpty("time");
		return this;
	}
       public InsectPestsCriteria andTimeEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public InsectPestsCriteria andTimeNotEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.NOT_EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public InsectPestsCriteria andTimeGreaterThan(java.util.Date value) {
          addCriterion("time", value, ConditionMode.GREATER_THEN, "time", "java.util.Date", "String");
          return this;
      }

      public InsectPestsCriteria andTimeGreaterThanOrEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.GREATER_EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public InsectPestsCriteria andTimeLessThan(java.util.Date value) {
          addCriterion("time", value, ConditionMode.LESS_THEN, "time", "java.util.Date", "String");
          return this;
      }

      public InsectPestsCriteria andTimeLessThanOrEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.LESS_EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public InsectPestsCriteria andTimeBetween(java.util.Date value1, java.util.Date value2) {
    	  addCriterion("time", value1, value2, ConditionMode.BETWEEN, "time", "java.util.Date", "String");
    	  return this;
      }

      public InsectPestsCriteria andTimeNotBetween(java.util.Date value1, java.util.Date value2) {
          addCriterion("time", value1, value2, ConditionMode.NOT_BETWEEN, "time", "java.util.Date", "String");
          return this;
      }
        
      public InsectPestsCriteria andTimeIn(List<java.util.Date> values) {
          addCriterion("time", values, ConditionMode.IN, "time", "java.util.Date", "String");
          return this;
      }

      public InsectPestsCriteria andTimeNotIn(List<java.util.Date> values) {
          addCriterion("time", values, ConditionMode.NOT_IN, "time", "java.util.Date", "String");
          return this;
      }
	public InsectPestsCriteria andImageIsNull() {
		isnull("image");
		return this;
	}
	
	public InsectPestsCriteria andImageIsNotNull() {
		notNull("image");
		return this;
	}
	
	public InsectPestsCriteria andImageIsEmpty() {
		empty("image");
		return this;
	}

	public InsectPestsCriteria andImageIsNotEmpty() {
		notEmpty("image");
		return this;
	}
        public InsectPestsCriteria andImageLike(java.lang.String value) {
    	   addCriterion("image", value, ConditionMode.FUZZY, "image", "java.lang.String", "String");
    	   return this;
      }

      public InsectPestsCriteria andImageNotLike(java.lang.String value) {
          addCriterion("image", value, ConditionMode.NOT_FUZZY, "image", "java.lang.String", "String");
          return this;
      }
      public InsectPestsCriteria andImageEqualTo(java.lang.String value) {
          addCriterion("image", value, ConditionMode.EQUAL, "image", "java.lang.String", "String");
          return this;
      }

      public InsectPestsCriteria andImageNotEqualTo(java.lang.String value) {
          addCriterion("image", value, ConditionMode.NOT_EQUAL, "image", "java.lang.String", "String");
          return this;
      }

      public InsectPestsCriteria andImageGreaterThan(java.lang.String value) {
          addCriterion("image", value, ConditionMode.GREATER_THEN, "image", "java.lang.String", "String");
          return this;
      }

      public InsectPestsCriteria andImageGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("image", value, ConditionMode.GREATER_EQUAL, "image", "java.lang.String", "String");
          return this;
      }

      public InsectPestsCriteria andImageLessThan(java.lang.String value) {
          addCriterion("image", value, ConditionMode.LESS_THEN, "image", "java.lang.String", "String");
          return this;
      }

      public InsectPestsCriteria andImageLessThanOrEqualTo(java.lang.String value) {
          addCriterion("image", value, ConditionMode.LESS_EQUAL, "image", "java.lang.String", "String");
          return this;
      }

      public InsectPestsCriteria andImageBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("image", value1, value2, ConditionMode.BETWEEN, "image", "java.lang.String", "String");
    	  return this;
      }

      public InsectPestsCriteria andImageNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("image", value1, value2, ConditionMode.NOT_BETWEEN, "image", "java.lang.String", "String");
          return this;
      }
        
      public InsectPestsCriteria andImageIn(List<java.lang.String> values) {
          addCriterion("image", values, ConditionMode.IN, "image", "java.lang.String", "String");
          return this;
      }

      public InsectPestsCriteria andImageNotIn(List<java.lang.String> values) {
          addCriterion("image", values, ConditionMode.NOT_IN, "image", "java.lang.String", "String");
          return this;
      }
}