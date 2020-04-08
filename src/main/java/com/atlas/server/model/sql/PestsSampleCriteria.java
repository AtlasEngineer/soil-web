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
public class PestsSampleCriteria extends Columns implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static PestsSampleCriteria create() {
		return new PestsSampleCriteria();
	}
	
	public static PestsSampleCriteria create(Column column) {
		PestsSampleCriteria that = new PestsSampleCriteria();
		that.add(column);
        return that;
    }

    public static PestsSampleCriteria create(String name, Object value) {
        return (PestsSampleCriteria) create().eq(name, value);
    }
    
    public Example example() {
    	return Example.create("at_pests_sample", this);
    }
    
    /**
     * equals
     *
     * @param name
     * @param value
     * @return
     */
    public PestsSampleCriteria eq(String name, Object value) {
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
    public PestsSampleCriteria ne(String name, Object value) {
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

    public PestsSampleCriteria like(String name, Object value) {
    	super.like(name, value);
        return this;
    }
    
    public PestsSampleCriteria notLike(String name, Object value) {
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
    public PestsSampleCriteria gt(String name, Object value) {
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
    public PestsSampleCriteria ge(String name, Object value) {
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
    public PestsSampleCriteria lt(String name, Object value) {
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
    public PestsSampleCriteria le(String name, Object value) {
    	super.le(name, value);
        return this;
    }
    
    public PestsSampleCriteria isnull(String name) {
    	super.isnull(name);
        return this;
    } 

    public PestsSampleCriteria notNull(String name) {
    	super.notNull(name);
        return this;
    } 
    
    public PestsSampleCriteria empty(String name) {
    	super.empty(name);
        return this;
    } 
    
    public PestsSampleCriteria notEmpty(String name) {
    	super.notEmpty(name);
        return this;
    } 
    
    public PestsSampleCriteria add(Column column) {
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
		 
	public PestsSampleCriteria andIdIsNull() {
		isnull("id");
		return this;
	}
	
	public PestsSampleCriteria andIdIsNotNull() {
		notNull("id");
		return this;
	}
	
	public PestsSampleCriteria andIdIsEmpty() {
		empty("id");
		return this;
	}

	public PestsSampleCriteria andIdIsNotEmpty() {
		notEmpty("id");
		return this;
	}
        public PestsSampleCriteria andIdLike(java.lang.String value) {
    	   addCriterion("id", value, ConditionMode.FUZZY, "id", "java.lang.String", "String");
    	   return this;
      }

      public PestsSampleCriteria andIdNotLike(java.lang.String value) {
          addCriterion("id", value, ConditionMode.NOT_FUZZY, "id", "java.lang.String", "String");
          return this;
      }
      public PestsSampleCriteria andIdEqualTo(java.lang.String value) {
          addCriterion("id", value, ConditionMode.EQUAL, "id", "java.lang.String", "String");
          return this;
      }

      public PestsSampleCriteria andIdNotEqualTo(java.lang.String value) {
          addCriterion("id", value, ConditionMode.NOT_EQUAL, "id", "java.lang.String", "String");
          return this;
      }

      public PestsSampleCriteria andIdGreaterThan(java.lang.String value) {
          addCriterion("id", value, ConditionMode.GREATER_THEN, "id", "java.lang.String", "String");
          return this;
      }

      public PestsSampleCriteria andIdGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("id", value, ConditionMode.GREATER_EQUAL, "id", "java.lang.String", "String");
          return this;
      }

      public PestsSampleCriteria andIdLessThan(java.lang.String value) {
          addCriterion("id", value, ConditionMode.LESS_THEN, "id", "java.lang.String", "String");
          return this;
      }

      public PestsSampleCriteria andIdLessThanOrEqualTo(java.lang.String value) {
          addCriterion("id", value, ConditionMode.LESS_EQUAL, "id", "java.lang.String", "String");
          return this;
      }

      public PestsSampleCriteria andIdBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("id", value1, value2, ConditionMode.BETWEEN, "id", "java.lang.String", "String");
    	  return this;
      }

      public PestsSampleCriteria andIdNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("id", value1, value2, ConditionMode.NOT_BETWEEN, "id", "java.lang.String", "String");
          return this;
      }
        
      public PestsSampleCriteria andIdIn(List<java.lang.String> values) {
          addCriterion("id", values, ConditionMode.IN, "id", "java.lang.String", "String");
          return this;
      }

      public PestsSampleCriteria andIdNotIn(List<java.lang.String> values) {
          addCriterion("id", values, ConditionMode.NOT_IN, "id", "java.lang.String", "String");
          return this;
      }
	public PestsSampleCriteria andPestsIdIsNull() {
		isnull("pests_id");
		return this;
	}
	
	public PestsSampleCriteria andPestsIdIsNotNull() {
		notNull("pests_id");
		return this;
	}
	
	public PestsSampleCriteria andPestsIdIsEmpty() {
		empty("pests_id");
		return this;
	}

	public PestsSampleCriteria andPestsIdIsNotEmpty() {
		notEmpty("pests_id");
		return this;
	}
       public PestsSampleCriteria andPestsIdEqualTo(java.lang.Integer value) {
          addCriterion("pests_id", value, ConditionMode.EQUAL, "pestsId", "java.lang.Integer", "Float");
          return this;
      }

      public PestsSampleCriteria andPestsIdNotEqualTo(java.lang.Integer value) {
          addCriterion("pests_id", value, ConditionMode.NOT_EQUAL, "pestsId", "java.lang.Integer", "Float");
          return this;
      }

      public PestsSampleCriteria andPestsIdGreaterThan(java.lang.Integer value) {
          addCriterion("pests_id", value, ConditionMode.GREATER_THEN, "pestsId", "java.lang.Integer", "Float");
          return this;
      }

      public PestsSampleCriteria andPestsIdGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("pests_id", value, ConditionMode.GREATER_EQUAL, "pestsId", "java.lang.Integer", "Float");
          return this;
      }

      public PestsSampleCriteria andPestsIdLessThan(java.lang.Integer value) {
          addCriterion("pests_id", value, ConditionMode.LESS_THEN, "pestsId", "java.lang.Integer", "Float");
          return this;
      }

      public PestsSampleCriteria andPestsIdLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("pests_id", value, ConditionMode.LESS_EQUAL, "pestsId", "java.lang.Integer", "Float");
          return this;
      }

      public PestsSampleCriteria andPestsIdBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("pests_id", value1, value2, ConditionMode.BETWEEN, "pestsId", "java.lang.Integer", "Float");
    	  return this;
      }

      public PestsSampleCriteria andPestsIdNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("pests_id", value1, value2, ConditionMode.NOT_BETWEEN, "pestsId", "java.lang.Integer", "Float");
          return this;
      }
        
      public PestsSampleCriteria andPestsIdIn(List<java.lang.Integer> values) {
          addCriterion("pests_id", values, ConditionMode.IN, "pestsId", "java.lang.Integer", "Float");
          return this;
      }

      public PestsSampleCriteria andPestsIdNotIn(List<java.lang.Integer> values) {
          addCriterion("pests_id", values, ConditionMode.NOT_IN, "pestsId", "java.lang.Integer", "Float");
          return this;
      }
	public PestsSampleCriteria andUrlIsNull() {
		isnull("url");
		return this;
	}
	
	public PestsSampleCriteria andUrlIsNotNull() {
		notNull("url");
		return this;
	}
	
	public PestsSampleCriteria andUrlIsEmpty() {
		empty("url");
		return this;
	}

	public PestsSampleCriteria andUrlIsNotEmpty() {
		notEmpty("url");
		return this;
	}
        public PestsSampleCriteria andUrlLike(java.lang.String value) {
    	   addCriterion("url", value, ConditionMode.FUZZY, "url", "java.lang.String", "Float");
    	   return this;
      }

      public PestsSampleCriteria andUrlNotLike(java.lang.String value) {
          addCriterion("url", value, ConditionMode.NOT_FUZZY, "url", "java.lang.String", "Float");
          return this;
      }
      public PestsSampleCriteria andUrlEqualTo(java.lang.String value) {
          addCriterion("url", value, ConditionMode.EQUAL, "url", "java.lang.String", "String");
          return this;
      }

      public PestsSampleCriteria andUrlNotEqualTo(java.lang.String value) {
          addCriterion("url", value, ConditionMode.NOT_EQUAL, "url", "java.lang.String", "String");
          return this;
      }

      public PestsSampleCriteria andUrlGreaterThan(java.lang.String value) {
          addCriterion("url", value, ConditionMode.GREATER_THEN, "url", "java.lang.String", "String");
          return this;
      }

      public PestsSampleCriteria andUrlGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("url", value, ConditionMode.GREATER_EQUAL, "url", "java.lang.String", "String");
          return this;
      }

      public PestsSampleCriteria andUrlLessThan(java.lang.String value) {
          addCriterion("url", value, ConditionMode.LESS_THEN, "url", "java.lang.String", "String");
          return this;
      }

      public PestsSampleCriteria andUrlLessThanOrEqualTo(java.lang.String value) {
          addCriterion("url", value, ConditionMode.LESS_EQUAL, "url", "java.lang.String", "String");
          return this;
      }

      public PestsSampleCriteria andUrlBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("url", value1, value2, ConditionMode.BETWEEN, "url", "java.lang.String", "String");
    	  return this;
      }

      public PestsSampleCriteria andUrlNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("url", value1, value2, ConditionMode.NOT_BETWEEN, "url", "java.lang.String", "String");
          return this;
      }
        
      public PestsSampleCriteria andUrlIn(List<java.lang.String> values) {
          addCriterion("url", values, ConditionMode.IN, "url", "java.lang.String", "String");
          return this;
      }

      public PestsSampleCriteria andUrlNotIn(List<java.lang.String> values) {
          addCriterion("url", values, ConditionMode.NOT_IN, "url", "java.lang.String", "String");
          return this;
      }
	public PestsSampleCriteria andBriefIsNull() {
		isnull("brief");
		return this;
	}
	
	public PestsSampleCriteria andBriefIsNotNull() {
		notNull("brief");
		return this;
	}
	
	public PestsSampleCriteria andBriefIsEmpty() {
		empty("brief");
		return this;
	}

	public PestsSampleCriteria andBriefIsNotEmpty() {
		notEmpty("brief");
		return this;
	}
        public PestsSampleCriteria andBriefLike(java.lang.String value) {
    	   addCriterion("brief", value, ConditionMode.FUZZY, "brief", "java.lang.String", "String");
    	   return this;
      }

      public PestsSampleCriteria andBriefNotLike(java.lang.String value) {
          addCriterion("brief", value, ConditionMode.NOT_FUZZY, "brief", "java.lang.String", "String");
          return this;
      }
      public PestsSampleCriteria andBriefEqualTo(java.lang.String value) {
          addCriterion("brief", value, ConditionMode.EQUAL, "brief", "java.lang.String", "String");
          return this;
      }

      public PestsSampleCriteria andBriefNotEqualTo(java.lang.String value) {
          addCriterion("brief", value, ConditionMode.NOT_EQUAL, "brief", "java.lang.String", "String");
          return this;
      }

      public PestsSampleCriteria andBriefGreaterThan(java.lang.String value) {
          addCriterion("brief", value, ConditionMode.GREATER_THEN, "brief", "java.lang.String", "String");
          return this;
      }

      public PestsSampleCriteria andBriefGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("brief", value, ConditionMode.GREATER_EQUAL, "brief", "java.lang.String", "String");
          return this;
      }

      public PestsSampleCriteria andBriefLessThan(java.lang.String value) {
          addCriterion("brief", value, ConditionMode.LESS_THEN, "brief", "java.lang.String", "String");
          return this;
      }

      public PestsSampleCriteria andBriefLessThanOrEqualTo(java.lang.String value) {
          addCriterion("brief", value, ConditionMode.LESS_EQUAL, "brief", "java.lang.String", "String");
          return this;
      }

      public PestsSampleCriteria andBriefBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("brief", value1, value2, ConditionMode.BETWEEN, "brief", "java.lang.String", "String");
    	  return this;
      }

      public PestsSampleCriteria andBriefNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("brief", value1, value2, ConditionMode.NOT_BETWEEN, "brief", "java.lang.String", "String");
          return this;
      }
        
      public PestsSampleCriteria andBriefIn(List<java.lang.String> values) {
          addCriterion("brief", values, ConditionMode.IN, "brief", "java.lang.String", "String");
          return this;
      }

      public PestsSampleCriteria andBriefNotIn(List<java.lang.String> values) {
          addCriterion("brief", values, ConditionMode.NOT_IN, "brief", "java.lang.String", "String");
          return this;
      }
	public PestsSampleCriteria andDelIsNull() {
		isnull("del");
		return this;
	}
	
	public PestsSampleCriteria andDelIsNotNull() {
		notNull("del");
		return this;
	}
	
	public PestsSampleCriteria andDelIsEmpty() {
		empty("del");
		return this;
	}

	public PestsSampleCriteria andDelIsNotEmpty() {
		notEmpty("del");
		return this;
	}
       public PestsSampleCriteria andDelEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public PestsSampleCriteria andDelNotEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.NOT_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public PestsSampleCriteria andDelGreaterThan(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.GREATER_THEN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public PestsSampleCriteria andDelGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.GREATER_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public PestsSampleCriteria andDelLessThan(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.LESS_THEN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public PestsSampleCriteria andDelLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.LESS_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public PestsSampleCriteria andDelBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("del", value1, value2, ConditionMode.BETWEEN, "del", "java.lang.Integer", "Float");
    	  return this;
      }

      public PestsSampleCriteria andDelNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("del", value1, value2, ConditionMode.NOT_BETWEEN, "del", "java.lang.Integer", "Float");
          return this;
      }
        
      public PestsSampleCriteria andDelIn(List<java.lang.Integer> values) {
          addCriterion("del", values, ConditionMode.IN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public PestsSampleCriteria andDelNotIn(List<java.lang.Integer> values) {
          addCriterion("del", values, ConditionMode.NOT_IN, "del", "java.lang.Integer", "Float");
          return this;
      }
	public PestsSampleCriteria andContSignIsNull() {
		isnull("cont_sign");
		return this;
	}
	
	public PestsSampleCriteria andContSignIsNotNull() {
		notNull("cont_sign");
		return this;
	}
	
	public PestsSampleCriteria andContSignIsEmpty() {
		empty("cont_sign");
		return this;
	}

	public PestsSampleCriteria andContSignIsNotEmpty() {
		notEmpty("cont_sign");
		return this;
	}
        public PestsSampleCriteria andContSignLike(java.lang.String value) {
    	   addCriterion("cont_sign", value, ConditionMode.FUZZY, "contSign", "java.lang.String", "Float");
    	   return this;
      }

      public PestsSampleCriteria andContSignNotLike(java.lang.String value) {
          addCriterion("cont_sign", value, ConditionMode.NOT_FUZZY, "contSign", "java.lang.String", "Float");
          return this;
      }
      public PestsSampleCriteria andContSignEqualTo(java.lang.String value) {
          addCriterion("cont_sign", value, ConditionMode.EQUAL, "contSign", "java.lang.String", "String");
          return this;
      }

      public PestsSampleCriteria andContSignNotEqualTo(java.lang.String value) {
          addCriterion("cont_sign", value, ConditionMode.NOT_EQUAL, "contSign", "java.lang.String", "String");
          return this;
      }

      public PestsSampleCriteria andContSignGreaterThan(java.lang.String value) {
          addCriterion("cont_sign", value, ConditionMode.GREATER_THEN, "contSign", "java.lang.String", "String");
          return this;
      }

      public PestsSampleCriteria andContSignGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("cont_sign", value, ConditionMode.GREATER_EQUAL, "contSign", "java.lang.String", "String");
          return this;
      }

      public PestsSampleCriteria andContSignLessThan(java.lang.String value) {
          addCriterion("cont_sign", value, ConditionMode.LESS_THEN, "contSign", "java.lang.String", "String");
          return this;
      }

      public PestsSampleCriteria andContSignLessThanOrEqualTo(java.lang.String value) {
          addCriterion("cont_sign", value, ConditionMode.LESS_EQUAL, "contSign", "java.lang.String", "String");
          return this;
      }

      public PestsSampleCriteria andContSignBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("cont_sign", value1, value2, ConditionMode.BETWEEN, "contSign", "java.lang.String", "String");
    	  return this;
      }

      public PestsSampleCriteria andContSignNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("cont_sign", value1, value2, ConditionMode.NOT_BETWEEN, "contSign", "java.lang.String", "String");
          return this;
      }
        
      public PestsSampleCriteria andContSignIn(List<java.lang.String> values) {
          addCriterion("cont_sign", values, ConditionMode.IN, "contSign", "java.lang.String", "String");
          return this;
      }

      public PestsSampleCriteria andContSignNotIn(List<java.lang.String> values) {
          addCriterion("cont_sign", values, ConditionMode.NOT_IN, "contSign", "java.lang.String", "String");
          return this;
      }
	public PestsSampleCriteria andLonIsNull() {
		isnull("lon");
		return this;
	}
	
	public PestsSampleCriteria andLonIsNotNull() {
		notNull("lon");
		return this;
	}
	
	public PestsSampleCriteria andLonIsEmpty() {
		empty("lon");
		return this;
	}

	public PestsSampleCriteria andLonIsNotEmpty() {
		notEmpty("lon");
		return this;
	}
       public PestsSampleCriteria andLonEqualTo(java.lang.Double value) {
          addCriterion("lon", value, ConditionMode.EQUAL, "lon", "java.lang.Double", "Float");
          return this;
      }

      public PestsSampleCriteria andLonNotEqualTo(java.lang.Double value) {
          addCriterion("lon", value, ConditionMode.NOT_EQUAL, "lon", "java.lang.Double", "Float");
          return this;
      }

      public PestsSampleCriteria andLonGreaterThan(java.lang.Double value) {
          addCriterion("lon", value, ConditionMode.GREATER_THEN, "lon", "java.lang.Double", "Float");
          return this;
      }

      public PestsSampleCriteria andLonGreaterThanOrEqualTo(java.lang.Double value) {
          addCriterion("lon", value, ConditionMode.GREATER_EQUAL, "lon", "java.lang.Double", "Float");
          return this;
      }

      public PestsSampleCriteria andLonLessThan(java.lang.Double value) {
          addCriterion("lon", value, ConditionMode.LESS_THEN, "lon", "java.lang.Double", "Float");
          return this;
      }

      public PestsSampleCriteria andLonLessThanOrEqualTo(java.lang.Double value) {
          addCriterion("lon", value, ConditionMode.LESS_EQUAL, "lon", "java.lang.Double", "Float");
          return this;
      }

      public PestsSampleCriteria andLonBetween(java.lang.Double value1, java.lang.Double value2) {
    	  addCriterion("lon", value1, value2, ConditionMode.BETWEEN, "lon", "java.lang.Double", "Float");
    	  return this;
      }

      public PestsSampleCriteria andLonNotBetween(java.lang.Double value1, java.lang.Double value2) {
          addCriterion("lon", value1, value2, ConditionMode.NOT_BETWEEN, "lon", "java.lang.Double", "Float");
          return this;
      }
        
      public PestsSampleCriteria andLonIn(List<java.lang.Double> values) {
          addCriterion("lon", values, ConditionMode.IN, "lon", "java.lang.Double", "Float");
          return this;
      }

      public PestsSampleCriteria andLonNotIn(List<java.lang.Double> values) {
          addCriterion("lon", values, ConditionMode.NOT_IN, "lon", "java.lang.Double", "Float");
          return this;
      }
	public PestsSampleCriteria andLatIsNull() {
		isnull("lat");
		return this;
	}
	
	public PestsSampleCriteria andLatIsNotNull() {
		notNull("lat");
		return this;
	}
	
	public PestsSampleCriteria andLatIsEmpty() {
		empty("lat");
		return this;
	}

	public PestsSampleCriteria andLatIsNotEmpty() {
		notEmpty("lat");
		return this;
	}
        public PestsSampleCriteria andLatLike(java.lang.String value) {
    	   addCriterion("lat", value, ConditionMode.FUZZY, "lat", "java.lang.String", "Float");
    	   return this;
      }

      public PestsSampleCriteria andLatNotLike(java.lang.String value) {
          addCriterion("lat", value, ConditionMode.NOT_FUZZY, "lat", "java.lang.String", "Float");
          return this;
      }
      public PestsSampleCriteria andLatEqualTo(java.lang.String value) {
          addCriterion("lat", value, ConditionMode.EQUAL, "lat", "java.lang.String", "String");
          return this;
      }

      public PestsSampleCriteria andLatNotEqualTo(java.lang.String value) {
          addCriterion("lat", value, ConditionMode.NOT_EQUAL, "lat", "java.lang.String", "String");
          return this;
      }

      public PestsSampleCriteria andLatGreaterThan(java.lang.String value) {
          addCriterion("lat", value, ConditionMode.GREATER_THEN, "lat", "java.lang.String", "String");
          return this;
      }

      public PestsSampleCriteria andLatGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("lat", value, ConditionMode.GREATER_EQUAL, "lat", "java.lang.String", "String");
          return this;
      }

      public PestsSampleCriteria andLatLessThan(java.lang.String value) {
          addCriterion("lat", value, ConditionMode.LESS_THEN, "lat", "java.lang.String", "String");
          return this;
      }

      public PestsSampleCriteria andLatLessThanOrEqualTo(java.lang.String value) {
          addCriterion("lat", value, ConditionMode.LESS_EQUAL, "lat", "java.lang.String", "String");
          return this;
      }

      public PestsSampleCriteria andLatBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("lat", value1, value2, ConditionMode.BETWEEN, "lat", "java.lang.String", "String");
    	  return this;
      }

      public PestsSampleCriteria andLatNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("lat", value1, value2, ConditionMode.NOT_BETWEEN, "lat", "java.lang.String", "String");
          return this;
      }
        
      public PestsSampleCriteria andLatIn(List<java.lang.String> values) {
          addCriterion("lat", values, ConditionMode.IN, "lat", "java.lang.String", "String");
          return this;
      }

      public PestsSampleCriteria andLatNotIn(List<java.lang.String> values) {
          addCriterion("lat", values, ConditionMode.NOT_IN, "lat", "java.lang.String", "String");
          return this;
      }
	public PestsSampleCriteria andTimeIsNull() {
		isnull("time");
		return this;
	}
	
	public PestsSampleCriteria andTimeIsNotNull() {
		notNull("time");
		return this;
	}
	
	public PestsSampleCriteria andTimeIsEmpty() {
		empty("time");
		return this;
	}

	public PestsSampleCriteria andTimeIsNotEmpty() {
		notEmpty("time");
		return this;
	}
       public PestsSampleCriteria andTimeEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public PestsSampleCriteria andTimeNotEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.NOT_EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public PestsSampleCriteria andTimeGreaterThan(java.util.Date value) {
          addCriterion("time", value, ConditionMode.GREATER_THEN, "time", "java.util.Date", "String");
          return this;
      }

      public PestsSampleCriteria andTimeGreaterThanOrEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.GREATER_EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public PestsSampleCriteria andTimeLessThan(java.util.Date value) {
          addCriterion("time", value, ConditionMode.LESS_THEN, "time", "java.util.Date", "String");
          return this;
      }

      public PestsSampleCriteria andTimeLessThanOrEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.LESS_EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public PestsSampleCriteria andTimeBetween(java.util.Date value1, java.util.Date value2) {
    	  addCriterion("time", value1, value2, ConditionMode.BETWEEN, "time", "java.util.Date", "String");
    	  return this;
      }

      public PestsSampleCriteria andTimeNotBetween(java.util.Date value1, java.util.Date value2) {
          addCriterion("time", value1, value2, ConditionMode.NOT_BETWEEN, "time", "java.util.Date", "String");
          return this;
      }
        
      public PestsSampleCriteria andTimeIn(List<java.util.Date> values) {
          addCriterion("time", values, ConditionMode.IN, "time", "java.util.Date", "String");
          return this;
      }

      public PestsSampleCriteria andTimeNotIn(List<java.util.Date> values) {
          addCriterion("time", values, ConditionMode.NOT_IN, "time", "java.util.Date", "String");
          return this;
      }
	public PestsSampleCriteria andTypeIsNull() {
		isnull("type");
		return this;
	}
	
	public PestsSampleCriteria andTypeIsNotNull() {
		notNull("type");
		return this;
	}
	
	public PestsSampleCriteria andTypeIsEmpty() {
		empty("type");
		return this;
	}

	public PestsSampleCriteria andTypeIsNotEmpty() {
		notEmpty("type");
		return this;
	}
       public PestsSampleCriteria andTypeEqualTo(java.lang.Integer value) {
          addCriterion("type", value, ConditionMode.EQUAL, "type", "java.lang.Integer", "Float");
          return this;
      }

      public PestsSampleCriteria andTypeNotEqualTo(java.lang.Integer value) {
          addCriterion("type", value, ConditionMode.NOT_EQUAL, "type", "java.lang.Integer", "Float");
          return this;
      }

      public PestsSampleCriteria andTypeGreaterThan(java.lang.Integer value) {
          addCriterion("type", value, ConditionMode.GREATER_THEN, "type", "java.lang.Integer", "Float");
          return this;
      }

      public PestsSampleCriteria andTypeGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("type", value, ConditionMode.GREATER_EQUAL, "type", "java.lang.Integer", "Float");
          return this;
      }

      public PestsSampleCriteria andTypeLessThan(java.lang.Integer value) {
          addCriterion("type", value, ConditionMode.LESS_THEN, "type", "java.lang.Integer", "Float");
          return this;
      }

      public PestsSampleCriteria andTypeLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("type", value, ConditionMode.LESS_EQUAL, "type", "java.lang.Integer", "Float");
          return this;
      }

      public PestsSampleCriteria andTypeBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("type", value1, value2, ConditionMode.BETWEEN, "type", "java.lang.Integer", "Float");
    	  return this;
      }

      public PestsSampleCriteria andTypeNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("type", value1, value2, ConditionMode.NOT_BETWEEN, "type", "java.lang.Integer", "Float");
          return this;
      }
        
      public PestsSampleCriteria andTypeIn(List<java.lang.Integer> values) {
          addCriterion("type", values, ConditionMode.IN, "type", "java.lang.Integer", "Float");
          return this;
      }

      public PestsSampleCriteria andTypeNotIn(List<java.lang.Integer> values) {
          addCriterion("type", values, ConditionMode.NOT_IN, "type", "java.lang.Integer", "Float");
          return this;
      }
	public PestsSampleCriteria andStatusIsNull() {
		isnull("status");
		return this;
	}
	
	public PestsSampleCriteria andStatusIsNotNull() {
		notNull("status");
		return this;
	}
	
	public PestsSampleCriteria andStatusIsEmpty() {
		empty("status");
		return this;
	}

	public PestsSampleCriteria andStatusIsNotEmpty() {
		notEmpty("status");
		return this;
	}
       public PestsSampleCriteria andStatusEqualTo(java.lang.Integer value) {
          addCriterion("status", value, ConditionMode.EQUAL, "status", "java.lang.Integer", "Float");
          return this;
      }

      public PestsSampleCriteria andStatusNotEqualTo(java.lang.Integer value) {
          addCriterion("status", value, ConditionMode.NOT_EQUAL, "status", "java.lang.Integer", "Float");
          return this;
      }

      public PestsSampleCriteria andStatusGreaterThan(java.lang.Integer value) {
          addCriterion("status", value, ConditionMode.GREATER_THEN, "status", "java.lang.Integer", "Float");
          return this;
      }

      public PestsSampleCriteria andStatusGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("status", value, ConditionMode.GREATER_EQUAL, "status", "java.lang.Integer", "Float");
          return this;
      }

      public PestsSampleCriteria andStatusLessThan(java.lang.Integer value) {
          addCriterion("status", value, ConditionMode.LESS_THEN, "status", "java.lang.Integer", "Float");
          return this;
      }

      public PestsSampleCriteria andStatusLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("status", value, ConditionMode.LESS_EQUAL, "status", "java.lang.Integer", "Float");
          return this;
      }

      public PestsSampleCriteria andStatusBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("status", value1, value2, ConditionMode.BETWEEN, "status", "java.lang.Integer", "Float");
    	  return this;
      }

      public PestsSampleCriteria andStatusNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("status", value1, value2, ConditionMode.NOT_BETWEEN, "status", "java.lang.Integer", "Float");
          return this;
      }
        
      public PestsSampleCriteria andStatusIn(List<java.lang.Integer> values) {
          addCriterion("status", values, ConditionMode.IN, "status", "java.lang.Integer", "Float");
          return this;
      }

      public PestsSampleCriteria andStatusNotIn(List<java.lang.Integer> values) {
          addCriterion("status", values, ConditionMode.NOT_IN, "status", "java.lang.Integer", "Float");
          return this;
      }
	public PestsSampleCriteria andNameIsNull() {
		isnull("name");
		return this;
	}
	
	public PestsSampleCriteria andNameIsNotNull() {
		notNull("name");
		return this;
	}
	
	public PestsSampleCriteria andNameIsEmpty() {
		empty("name");
		return this;
	}

	public PestsSampleCriteria andNameIsNotEmpty() {
		notEmpty("name");
		return this;
	}
        public PestsSampleCriteria andNameLike(java.lang.String value) {
    	   addCriterion("name", value, ConditionMode.FUZZY, "name", "java.lang.String", "Float");
    	   return this;
      }

      public PestsSampleCriteria andNameNotLike(java.lang.String value) {
          addCriterion("name", value, ConditionMode.NOT_FUZZY, "name", "java.lang.String", "Float");
          return this;
      }
      public PestsSampleCriteria andNameEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public PestsSampleCriteria andNameNotEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.NOT_EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public PestsSampleCriteria andNameGreaterThan(java.lang.String value) {
          addCriterion("name", value, ConditionMode.GREATER_THEN, "name", "java.lang.String", "String");
          return this;
      }

      public PestsSampleCriteria andNameGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.GREATER_EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public PestsSampleCriteria andNameLessThan(java.lang.String value) {
          addCriterion("name", value, ConditionMode.LESS_THEN, "name", "java.lang.String", "String");
          return this;
      }

      public PestsSampleCriteria andNameLessThanOrEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.LESS_EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public PestsSampleCriteria andNameBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("name", value1, value2, ConditionMode.BETWEEN, "name", "java.lang.String", "String");
    	  return this;
      }

      public PestsSampleCriteria andNameNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("name", value1, value2, ConditionMode.NOT_BETWEEN, "name", "java.lang.String", "String");
          return this;
      }
        
      public PestsSampleCriteria andNameIn(List<java.lang.String> values) {
          addCriterion("name", values, ConditionMode.IN, "name", "java.lang.String", "String");
          return this;
      }

      public PestsSampleCriteria andNameNotIn(List<java.lang.String> values) {
          addCriterion("name", values, ConditionMode.NOT_IN, "name", "java.lang.String", "String");
          return this;
      }
}