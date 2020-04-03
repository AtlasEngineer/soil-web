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
public class SignCriteria extends Columns implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static SignCriteria create() {
		return new SignCriteria();
	}
	
	public static SignCriteria create(Column column) {
		SignCriteria that = new SignCriteria();
		that.add(column);
        return that;
    }

    public static SignCriteria create(String name, Object value) {
        return (SignCriteria) create().eq(name, value);
    }
    
    public Example example() {
    	return Example.create("at_sign", this);
    }
    
    /**
     * equals
     *
     * @param name
     * @param value
     * @return
     */
    public SignCriteria eq(String name, Object value) {
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
    public SignCriteria ne(String name, Object value) {
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

    public SignCriteria like(String name, Object value) {
    	super.like(name, value);
        return this;
    }
    
    public SignCriteria notLike(String name, Object value) {
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
    public SignCriteria gt(String name, Object value) {
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
    public SignCriteria ge(String name, Object value) {
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
    public SignCriteria lt(String name, Object value) {
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
    public SignCriteria le(String name, Object value) {
    	super.le(name, value);
        return this;
    }
    
    public SignCriteria isnull(String name) {
    	super.isnull(name);
        return this;
    } 

    public SignCriteria notNull(String name) {
    	super.notNull(name);
        return this;
    } 
    
    public SignCriteria empty(String name) {
    	super.empty(name);
        return this;
    } 
    
    public SignCriteria notEmpty(String name) {
    	super.notEmpty(name);
        return this;
    } 
    
    public SignCriteria add(Column column) {
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
		 
	public SignCriteria andIdIsNull() {
		isnull("id");
		return this;
	}
	
	public SignCriteria andIdIsNotNull() {
		notNull("id");
		return this;
	}
	
	public SignCriteria andIdIsEmpty() {
		empty("id");
		return this;
	}

	public SignCriteria andIdIsNotEmpty() {
		notEmpty("id");
		return this;
	}
       public SignCriteria andIdEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public SignCriteria andIdNotEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.NOT_EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public SignCriteria andIdGreaterThan(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.GREATER_THEN, "id", "java.lang.Integer", "Float");
          return this;
      }

      public SignCriteria andIdGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.GREATER_EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public SignCriteria andIdLessThan(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.LESS_THEN, "id", "java.lang.Integer", "Float");
          return this;
      }

      public SignCriteria andIdLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.LESS_EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public SignCriteria andIdBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("id", value1, value2, ConditionMode.BETWEEN, "id", "java.lang.Integer", "Float");
    	  return this;
      }

      public SignCriteria andIdNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("id", value1, value2, ConditionMode.NOT_BETWEEN, "id", "java.lang.Integer", "Float");
          return this;
      }
        
      public SignCriteria andIdIn(List<java.lang.Integer> values) {
          addCriterion("id", values, ConditionMode.IN, "id", "java.lang.Integer", "Float");
          return this;
      }

      public SignCriteria andIdNotIn(List<java.lang.Integer> values) {
          addCriterion("id", values, ConditionMode.NOT_IN, "id", "java.lang.Integer", "Float");
          return this;
      }
	public SignCriteria andUseridIsNull() {
		isnull("userid");
		return this;
	}
	
	public SignCriteria andUseridIsNotNull() {
		notNull("userid");
		return this;
	}
	
	public SignCriteria andUseridIsEmpty() {
		empty("userid");
		return this;
	}

	public SignCriteria andUseridIsNotEmpty() {
		notEmpty("userid");
		return this;
	}
       public SignCriteria andUseridEqualTo(java.lang.Integer value) {
          addCriterion("userid", value, ConditionMode.EQUAL, "userid", "java.lang.Integer", "Float");
          return this;
      }

      public SignCriteria andUseridNotEqualTo(java.lang.Integer value) {
          addCriterion("userid", value, ConditionMode.NOT_EQUAL, "userid", "java.lang.Integer", "Float");
          return this;
      }

      public SignCriteria andUseridGreaterThan(java.lang.Integer value) {
          addCriterion("userid", value, ConditionMode.GREATER_THEN, "userid", "java.lang.Integer", "Float");
          return this;
      }

      public SignCriteria andUseridGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("userid", value, ConditionMode.GREATER_EQUAL, "userid", "java.lang.Integer", "Float");
          return this;
      }

      public SignCriteria andUseridLessThan(java.lang.Integer value) {
          addCriterion("userid", value, ConditionMode.LESS_THEN, "userid", "java.lang.Integer", "Float");
          return this;
      }

      public SignCriteria andUseridLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("userid", value, ConditionMode.LESS_EQUAL, "userid", "java.lang.Integer", "Float");
          return this;
      }

      public SignCriteria andUseridBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("userid", value1, value2, ConditionMode.BETWEEN, "userid", "java.lang.Integer", "Float");
    	  return this;
      }

      public SignCriteria andUseridNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("userid", value1, value2, ConditionMode.NOT_BETWEEN, "userid", "java.lang.Integer", "Float");
          return this;
      }
        
      public SignCriteria andUseridIn(List<java.lang.Integer> values) {
          addCriterion("userid", values, ConditionMode.IN, "userid", "java.lang.Integer", "Float");
          return this;
      }

      public SignCriteria andUseridNotIn(List<java.lang.Integer> values) {
          addCriterion("userid", values, ConditionMode.NOT_IN, "userid", "java.lang.Integer", "Float");
          return this;
      }
	public SignCriteria andLngIsNull() {
		isnull("lng");
		return this;
	}
	
	public SignCriteria andLngIsNotNull() {
		notNull("lng");
		return this;
	}
	
	public SignCriteria andLngIsEmpty() {
		empty("lng");
		return this;
	}

	public SignCriteria andLngIsNotEmpty() {
		notEmpty("lng");
		return this;
	}
       public SignCriteria andLngEqualTo(java.math.BigDecimal value) {
          addCriterion("lng", value, ConditionMode.EQUAL, "lng", "java.math.BigDecimal", "Float");
          return this;
      }

      public SignCriteria andLngNotEqualTo(java.math.BigDecimal value) {
          addCriterion("lng", value, ConditionMode.NOT_EQUAL, "lng", "java.math.BigDecimal", "Float");
          return this;
      }

      public SignCriteria andLngGreaterThan(java.math.BigDecimal value) {
          addCriterion("lng", value, ConditionMode.GREATER_THEN, "lng", "java.math.BigDecimal", "Float");
          return this;
      }

      public SignCriteria andLngGreaterThanOrEqualTo(java.math.BigDecimal value) {
          addCriterion("lng", value, ConditionMode.GREATER_EQUAL, "lng", "java.math.BigDecimal", "Float");
          return this;
      }

      public SignCriteria andLngLessThan(java.math.BigDecimal value) {
          addCriterion("lng", value, ConditionMode.LESS_THEN, "lng", "java.math.BigDecimal", "Float");
          return this;
      }

      public SignCriteria andLngLessThanOrEqualTo(java.math.BigDecimal value) {
          addCriterion("lng", value, ConditionMode.LESS_EQUAL, "lng", "java.math.BigDecimal", "Float");
          return this;
      }

      public SignCriteria andLngBetween(java.math.BigDecimal value1, java.math.BigDecimal value2) {
    	  addCriterion("lng", value1, value2, ConditionMode.BETWEEN, "lng", "java.math.BigDecimal", "Float");
    	  return this;
      }

      public SignCriteria andLngNotBetween(java.math.BigDecimal value1, java.math.BigDecimal value2) {
          addCriterion("lng", value1, value2, ConditionMode.NOT_BETWEEN, "lng", "java.math.BigDecimal", "Float");
          return this;
      }
        
      public SignCriteria andLngIn(List<java.math.BigDecimal> values) {
          addCriterion("lng", values, ConditionMode.IN, "lng", "java.math.BigDecimal", "Float");
          return this;
      }

      public SignCriteria andLngNotIn(List<java.math.BigDecimal> values) {
          addCriterion("lng", values, ConditionMode.NOT_IN, "lng", "java.math.BigDecimal", "Float");
          return this;
      }
	public SignCriteria andLatIsNull() {
		isnull("lat");
		return this;
	}
	
	public SignCriteria andLatIsNotNull() {
		notNull("lat");
		return this;
	}
	
	public SignCriteria andLatIsEmpty() {
		empty("lat");
		return this;
	}

	public SignCriteria andLatIsNotEmpty() {
		notEmpty("lat");
		return this;
	}
       public SignCriteria andLatEqualTo(java.math.BigDecimal value) {
          addCriterion("lat", value, ConditionMode.EQUAL, "lat", "java.math.BigDecimal", "Float");
          return this;
      }

      public SignCriteria andLatNotEqualTo(java.math.BigDecimal value) {
          addCriterion("lat", value, ConditionMode.NOT_EQUAL, "lat", "java.math.BigDecimal", "Float");
          return this;
      }

      public SignCriteria andLatGreaterThan(java.math.BigDecimal value) {
          addCriterion("lat", value, ConditionMode.GREATER_THEN, "lat", "java.math.BigDecimal", "Float");
          return this;
      }

      public SignCriteria andLatGreaterThanOrEqualTo(java.math.BigDecimal value) {
          addCriterion("lat", value, ConditionMode.GREATER_EQUAL, "lat", "java.math.BigDecimal", "Float");
          return this;
      }

      public SignCriteria andLatLessThan(java.math.BigDecimal value) {
          addCriterion("lat", value, ConditionMode.LESS_THEN, "lat", "java.math.BigDecimal", "Float");
          return this;
      }

      public SignCriteria andLatLessThanOrEqualTo(java.math.BigDecimal value) {
          addCriterion("lat", value, ConditionMode.LESS_EQUAL, "lat", "java.math.BigDecimal", "Float");
          return this;
      }

      public SignCriteria andLatBetween(java.math.BigDecimal value1, java.math.BigDecimal value2) {
    	  addCriterion("lat", value1, value2, ConditionMode.BETWEEN, "lat", "java.math.BigDecimal", "Float");
    	  return this;
      }

      public SignCriteria andLatNotBetween(java.math.BigDecimal value1, java.math.BigDecimal value2) {
          addCriterion("lat", value1, value2, ConditionMode.NOT_BETWEEN, "lat", "java.math.BigDecimal", "Float");
          return this;
      }
        
      public SignCriteria andLatIn(List<java.math.BigDecimal> values) {
          addCriterion("lat", values, ConditionMode.IN, "lat", "java.math.BigDecimal", "Float");
          return this;
      }

      public SignCriteria andLatNotIn(List<java.math.BigDecimal> values) {
          addCriterion("lat", values, ConditionMode.NOT_IN, "lat", "java.math.BigDecimal", "Float");
          return this;
      }
	public SignCriteria andTypeIsNull() {
		isnull("type");
		return this;
	}
	
	public SignCriteria andTypeIsNotNull() {
		notNull("type");
		return this;
	}
	
	public SignCriteria andTypeIsEmpty() {
		empty("type");
		return this;
	}

	public SignCriteria andTypeIsNotEmpty() {
		notEmpty("type");
		return this;
	}
        public SignCriteria andTypeLike(java.lang.String value) {
    	   addCriterion("type", value, ConditionMode.FUZZY, "type", "java.lang.String", "Float");
    	   return this;
      }

      public SignCriteria andTypeNotLike(java.lang.String value) {
          addCriterion("type", value, ConditionMode.NOT_FUZZY, "type", "java.lang.String", "Float");
          return this;
      }
      public SignCriteria andTypeEqualTo(java.lang.String value) {
          addCriterion("type", value, ConditionMode.EQUAL, "type", "java.lang.String", "String");
          return this;
      }

      public SignCriteria andTypeNotEqualTo(java.lang.String value) {
          addCriterion("type", value, ConditionMode.NOT_EQUAL, "type", "java.lang.String", "String");
          return this;
      }

      public SignCriteria andTypeGreaterThan(java.lang.String value) {
          addCriterion("type", value, ConditionMode.GREATER_THEN, "type", "java.lang.String", "String");
          return this;
      }

      public SignCriteria andTypeGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("type", value, ConditionMode.GREATER_EQUAL, "type", "java.lang.String", "String");
          return this;
      }

      public SignCriteria andTypeLessThan(java.lang.String value) {
          addCriterion("type", value, ConditionMode.LESS_THEN, "type", "java.lang.String", "String");
          return this;
      }

      public SignCriteria andTypeLessThanOrEqualTo(java.lang.String value) {
          addCriterion("type", value, ConditionMode.LESS_EQUAL, "type", "java.lang.String", "String");
          return this;
      }

      public SignCriteria andTypeBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("type", value1, value2, ConditionMode.BETWEEN, "type", "java.lang.String", "String");
    	  return this;
      }

      public SignCriteria andTypeNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("type", value1, value2, ConditionMode.NOT_BETWEEN, "type", "java.lang.String", "String");
          return this;
      }
        
      public SignCriteria andTypeIn(List<java.lang.String> values) {
          addCriterion("type", values, ConditionMode.IN, "type", "java.lang.String", "String");
          return this;
      }

      public SignCriteria andTypeNotIn(List<java.lang.String> values) {
          addCriterion("type", values, ConditionMode.NOT_IN, "type", "java.lang.String", "String");
          return this;
      }
	public SignCriteria andOriginIsNull() {
		isnull("origin");
		return this;
	}
	
	public SignCriteria andOriginIsNotNull() {
		notNull("origin");
		return this;
	}
	
	public SignCriteria andOriginIsEmpty() {
		empty("origin");
		return this;
	}

	public SignCriteria andOriginIsNotEmpty() {
		notEmpty("origin");
		return this;
	}
        public SignCriteria andOriginLike(java.lang.String value) {
    	   addCriterion("origin", value, ConditionMode.FUZZY, "origin", "java.lang.String", "String");
    	   return this;
      }

      public SignCriteria andOriginNotLike(java.lang.String value) {
          addCriterion("origin", value, ConditionMode.NOT_FUZZY, "origin", "java.lang.String", "String");
          return this;
      }
      public SignCriteria andOriginEqualTo(java.lang.String value) {
          addCriterion("origin", value, ConditionMode.EQUAL, "origin", "java.lang.String", "String");
          return this;
      }

      public SignCriteria andOriginNotEqualTo(java.lang.String value) {
          addCriterion("origin", value, ConditionMode.NOT_EQUAL, "origin", "java.lang.String", "String");
          return this;
      }

      public SignCriteria andOriginGreaterThan(java.lang.String value) {
          addCriterion("origin", value, ConditionMode.GREATER_THEN, "origin", "java.lang.String", "String");
          return this;
      }

      public SignCriteria andOriginGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("origin", value, ConditionMode.GREATER_EQUAL, "origin", "java.lang.String", "String");
          return this;
      }

      public SignCriteria andOriginLessThan(java.lang.String value) {
          addCriterion("origin", value, ConditionMode.LESS_THEN, "origin", "java.lang.String", "String");
          return this;
      }

      public SignCriteria andOriginLessThanOrEqualTo(java.lang.String value) {
          addCriterion("origin", value, ConditionMode.LESS_EQUAL, "origin", "java.lang.String", "String");
          return this;
      }

      public SignCriteria andOriginBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("origin", value1, value2, ConditionMode.BETWEEN, "origin", "java.lang.String", "String");
    	  return this;
      }

      public SignCriteria andOriginNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("origin", value1, value2, ConditionMode.NOT_BETWEEN, "origin", "java.lang.String", "String");
          return this;
      }
        
      public SignCriteria andOriginIn(List<java.lang.String> values) {
          addCriterion("origin", values, ConditionMode.IN, "origin", "java.lang.String", "String");
          return this;
      }

      public SignCriteria andOriginNotIn(List<java.lang.String> values) {
          addCriterion("origin", values, ConditionMode.NOT_IN, "origin", "java.lang.String", "String");
          return this;
      }
	public SignCriteria andAddressIsNull() {
		isnull("address");
		return this;
	}
	
	public SignCriteria andAddressIsNotNull() {
		notNull("address");
		return this;
	}
	
	public SignCriteria andAddressIsEmpty() {
		empty("address");
		return this;
	}

	public SignCriteria andAddressIsNotEmpty() {
		notEmpty("address");
		return this;
	}
        public SignCriteria andAddressLike(java.lang.String value) {
    	   addCriterion("address", value, ConditionMode.FUZZY, "address", "java.lang.String", "String");
    	   return this;
      }

      public SignCriteria andAddressNotLike(java.lang.String value) {
          addCriterion("address", value, ConditionMode.NOT_FUZZY, "address", "java.lang.String", "String");
          return this;
      }
      public SignCriteria andAddressEqualTo(java.lang.String value) {
          addCriterion("address", value, ConditionMode.EQUAL, "address", "java.lang.String", "String");
          return this;
      }

      public SignCriteria andAddressNotEqualTo(java.lang.String value) {
          addCriterion("address", value, ConditionMode.NOT_EQUAL, "address", "java.lang.String", "String");
          return this;
      }

      public SignCriteria andAddressGreaterThan(java.lang.String value) {
          addCriterion("address", value, ConditionMode.GREATER_THEN, "address", "java.lang.String", "String");
          return this;
      }

      public SignCriteria andAddressGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("address", value, ConditionMode.GREATER_EQUAL, "address", "java.lang.String", "String");
          return this;
      }

      public SignCriteria andAddressLessThan(java.lang.String value) {
          addCriterion("address", value, ConditionMode.LESS_THEN, "address", "java.lang.String", "String");
          return this;
      }

      public SignCriteria andAddressLessThanOrEqualTo(java.lang.String value) {
          addCriterion("address", value, ConditionMode.LESS_EQUAL, "address", "java.lang.String", "String");
          return this;
      }

      public SignCriteria andAddressBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("address", value1, value2, ConditionMode.BETWEEN, "address", "java.lang.String", "String");
    	  return this;
      }

      public SignCriteria andAddressNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("address", value1, value2, ConditionMode.NOT_BETWEEN, "address", "java.lang.String", "String");
          return this;
      }
        
      public SignCriteria andAddressIn(List<java.lang.String> values) {
          addCriterion("address", values, ConditionMode.IN, "address", "java.lang.String", "String");
          return this;
      }

      public SignCriteria andAddressNotIn(List<java.lang.String> values) {
          addCriterion("address", values, ConditionMode.NOT_IN, "address", "java.lang.String", "String");
          return this;
      }
	public SignCriteria andCreatedIsNull() {
		isnull("created");
		return this;
	}
	
	public SignCriteria andCreatedIsNotNull() {
		notNull("created");
		return this;
	}
	
	public SignCriteria andCreatedIsEmpty() {
		empty("created");
		return this;
	}

	public SignCriteria andCreatedIsNotEmpty() {
		notEmpty("created");
		return this;
	}
       public SignCriteria andCreatedEqualTo(java.util.Date value) {
          addCriterion("created", value, ConditionMode.EQUAL, "created", "java.util.Date", "String");
          return this;
      }

      public SignCriteria andCreatedNotEqualTo(java.util.Date value) {
          addCriterion("created", value, ConditionMode.NOT_EQUAL, "created", "java.util.Date", "String");
          return this;
      }

      public SignCriteria andCreatedGreaterThan(java.util.Date value) {
          addCriterion("created", value, ConditionMode.GREATER_THEN, "created", "java.util.Date", "String");
          return this;
      }

      public SignCriteria andCreatedGreaterThanOrEqualTo(java.util.Date value) {
          addCriterion("created", value, ConditionMode.GREATER_EQUAL, "created", "java.util.Date", "String");
          return this;
      }

      public SignCriteria andCreatedLessThan(java.util.Date value) {
          addCriterion("created", value, ConditionMode.LESS_THEN, "created", "java.util.Date", "String");
          return this;
      }

      public SignCriteria andCreatedLessThanOrEqualTo(java.util.Date value) {
          addCriterion("created", value, ConditionMode.LESS_EQUAL, "created", "java.util.Date", "String");
          return this;
      }

      public SignCriteria andCreatedBetween(java.util.Date value1, java.util.Date value2) {
    	  addCriterion("created", value1, value2, ConditionMode.BETWEEN, "created", "java.util.Date", "String");
    	  return this;
      }

      public SignCriteria andCreatedNotBetween(java.util.Date value1, java.util.Date value2) {
          addCriterion("created", value1, value2, ConditionMode.NOT_BETWEEN, "created", "java.util.Date", "String");
          return this;
      }
        
      public SignCriteria andCreatedIn(List<java.util.Date> values) {
          addCriterion("created", values, ConditionMode.IN, "created", "java.util.Date", "String");
          return this;
      }

      public SignCriteria andCreatedNotIn(List<java.util.Date> values) {
          addCriterion("created", values, ConditionMode.NOT_IN, "created", "java.util.Date", "String");
          return this;
      }
}