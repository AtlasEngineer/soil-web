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
public class AtBotanyCriteria extends Columns implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static AtBotanyCriteria create() {
		return new AtBotanyCriteria();
	}
	
	public static AtBotanyCriteria create(Column column) {
		AtBotanyCriteria that = new AtBotanyCriteria();
		that.add(column);
        return that;
    }

    public static AtBotanyCriteria create(String name, Object value) {
        return (AtBotanyCriteria) create().eq(name, value);
    }
    
    public Example example() {
    	return Example.create("at_botany", this);
    }
    
    /**
     * equals
     *
     * @param name
     * @param value
     * @return
     */
    public AtBotanyCriteria eq(String name, Object value) {
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
    public AtBotanyCriteria ne(String name, Object value) {
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

    public AtBotanyCriteria like(String name, Object value) {
    	super.like(name, value);
        return this;
    }
    
    public AtBotanyCriteria notLike(String name, Object value) {
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
    public AtBotanyCriteria gt(String name, Object value) {
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
    public AtBotanyCriteria ge(String name, Object value) {
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
    public AtBotanyCriteria lt(String name, Object value) {
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
    public AtBotanyCriteria le(String name, Object value) {
    	super.le(name, value);
        return this;
    }
    
    public AtBotanyCriteria isnull(String name) {
    	super.isnull(name);
        return this;
    } 

    public AtBotanyCriteria notNull(String name) {
    	super.notNull(name);
        return this;
    } 
    
    public AtBotanyCriteria empty(String name) {
    	super.empty(name);
        return this;
    } 
    
    public AtBotanyCriteria notEmpty(String name) {
    	super.notEmpty(name);
        return this;
    } 
    
    public AtBotanyCriteria add(Column column) {
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
		 
	public AtBotanyCriteria andIdIsNull() {
		isnull("id");
		return this;
	}
	
	public AtBotanyCriteria andIdIsNotNull() {
		notNull("id");
		return this;
	}
	
	public AtBotanyCriteria andIdIsEmpty() {
		empty("id");
		return this;
	}

	public AtBotanyCriteria andIdIsNotEmpty() {
		notEmpty("id");
		return this;
	}
       public AtBotanyCriteria andIdEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public AtBotanyCriteria andIdNotEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.NOT_EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public AtBotanyCriteria andIdGreaterThan(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.GREATER_THEN, "id", "java.lang.Integer", "Float");
          return this;
      }

      public AtBotanyCriteria andIdGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.GREATER_EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public AtBotanyCriteria andIdLessThan(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.LESS_THEN, "id", "java.lang.Integer", "Float");
          return this;
      }

      public AtBotanyCriteria andIdLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.LESS_EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public AtBotanyCriteria andIdBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("id", value1, value2, ConditionMode.BETWEEN, "id", "java.lang.Integer", "Float");
    	  return this;
      }

      public AtBotanyCriteria andIdNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("id", value1, value2, ConditionMode.NOT_BETWEEN, "id", "java.lang.Integer", "Float");
          return this;
      }
        
      public AtBotanyCriteria andIdIn(List<java.lang.Integer> values) {
          addCriterion("id", values, ConditionMode.IN, "id", "java.lang.Integer", "Float");
          return this;
      }

      public AtBotanyCriteria andIdNotIn(List<java.lang.Integer> values) {
          addCriterion("id", values, ConditionMode.NOT_IN, "id", "java.lang.Integer", "Float");
          return this;
      }
	public AtBotanyCriteria andDelIsNull() {
		isnull("del");
		return this;
	}
	
	public AtBotanyCriteria andDelIsNotNull() {
		notNull("del");
		return this;
	}
	
	public AtBotanyCriteria andDelIsEmpty() {
		empty("del");
		return this;
	}

	public AtBotanyCriteria andDelIsNotEmpty() {
		notEmpty("del");
		return this;
	}
       public AtBotanyCriteria andDelEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public AtBotanyCriteria andDelNotEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.NOT_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public AtBotanyCriteria andDelGreaterThan(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.GREATER_THEN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public AtBotanyCriteria andDelGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.GREATER_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public AtBotanyCriteria andDelLessThan(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.LESS_THEN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public AtBotanyCriteria andDelLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.LESS_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public AtBotanyCriteria andDelBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("del", value1, value2, ConditionMode.BETWEEN, "del", "java.lang.Integer", "Float");
    	  return this;
      }

      public AtBotanyCriteria andDelNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("del", value1, value2, ConditionMode.NOT_BETWEEN, "del", "java.lang.Integer", "Float");
          return this;
      }
        
      public AtBotanyCriteria andDelIn(List<java.lang.Integer> values) {
          addCriterion("del", values, ConditionMode.IN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public AtBotanyCriteria andDelNotIn(List<java.lang.Integer> values) {
          addCriterion("del", values, ConditionMode.NOT_IN, "del", "java.lang.Integer", "Float");
          return this;
      }
	public AtBotanyCriteria andCatalogueIdIsNull() {
		isnull("catalogue_id");
		return this;
	}
	
	public AtBotanyCriteria andCatalogueIdIsNotNull() {
		notNull("catalogue_id");
		return this;
	}
	
	public AtBotanyCriteria andCatalogueIdIsEmpty() {
		empty("catalogue_id");
		return this;
	}

	public AtBotanyCriteria andCatalogueIdIsNotEmpty() {
		notEmpty("catalogue_id");
		return this;
	}
       public AtBotanyCriteria andCatalogueIdEqualTo(java.lang.Integer value) {
          addCriterion("catalogue_id", value, ConditionMode.EQUAL, "catalogueId", "java.lang.Integer", "Float");
          return this;
      }

      public AtBotanyCriteria andCatalogueIdNotEqualTo(java.lang.Integer value) {
          addCriterion("catalogue_id", value, ConditionMode.NOT_EQUAL, "catalogueId", "java.lang.Integer", "Float");
          return this;
      }

      public AtBotanyCriteria andCatalogueIdGreaterThan(java.lang.Integer value) {
          addCriterion("catalogue_id", value, ConditionMode.GREATER_THEN, "catalogueId", "java.lang.Integer", "Float");
          return this;
      }

      public AtBotanyCriteria andCatalogueIdGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("catalogue_id", value, ConditionMode.GREATER_EQUAL, "catalogueId", "java.lang.Integer", "Float");
          return this;
      }

      public AtBotanyCriteria andCatalogueIdLessThan(java.lang.Integer value) {
          addCriterion("catalogue_id", value, ConditionMode.LESS_THEN, "catalogueId", "java.lang.Integer", "Float");
          return this;
      }

      public AtBotanyCriteria andCatalogueIdLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("catalogue_id", value, ConditionMode.LESS_EQUAL, "catalogueId", "java.lang.Integer", "Float");
          return this;
      }

      public AtBotanyCriteria andCatalogueIdBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("catalogue_id", value1, value2, ConditionMode.BETWEEN, "catalogueId", "java.lang.Integer", "Float");
    	  return this;
      }

      public AtBotanyCriteria andCatalogueIdNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("catalogue_id", value1, value2, ConditionMode.NOT_BETWEEN, "catalogueId", "java.lang.Integer", "Float");
          return this;
      }
        
      public AtBotanyCriteria andCatalogueIdIn(List<java.lang.Integer> values) {
          addCriterion("catalogue_id", values, ConditionMode.IN, "catalogueId", "java.lang.Integer", "Float");
          return this;
      }

      public AtBotanyCriteria andCatalogueIdNotIn(List<java.lang.Integer> values) {
          addCriterion("catalogue_id", values, ConditionMode.NOT_IN, "catalogueId", "java.lang.Integer", "Float");
          return this;
      }
	public AtBotanyCriteria andSampleIdIsNull() {
		isnull("sample_id");
		return this;
	}
	
	public AtBotanyCriteria andSampleIdIsNotNull() {
		notNull("sample_id");
		return this;
	}
	
	public AtBotanyCriteria andSampleIdIsEmpty() {
		empty("sample_id");
		return this;
	}

	public AtBotanyCriteria andSampleIdIsNotEmpty() {
		notEmpty("sample_id");
		return this;
	}
       public AtBotanyCriteria andSampleIdEqualTo(java.lang.Integer value) {
          addCriterion("sample_id", value, ConditionMode.EQUAL, "sampleId", "java.lang.Integer", "Float");
          return this;
      }

      public AtBotanyCriteria andSampleIdNotEqualTo(java.lang.Integer value) {
          addCriterion("sample_id", value, ConditionMode.NOT_EQUAL, "sampleId", "java.lang.Integer", "Float");
          return this;
      }

      public AtBotanyCriteria andSampleIdGreaterThan(java.lang.Integer value) {
          addCriterion("sample_id", value, ConditionMode.GREATER_THEN, "sampleId", "java.lang.Integer", "Float");
          return this;
      }

      public AtBotanyCriteria andSampleIdGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("sample_id", value, ConditionMode.GREATER_EQUAL, "sampleId", "java.lang.Integer", "Float");
          return this;
      }

      public AtBotanyCriteria andSampleIdLessThan(java.lang.Integer value) {
          addCriterion("sample_id", value, ConditionMode.LESS_THEN, "sampleId", "java.lang.Integer", "Float");
          return this;
      }

      public AtBotanyCriteria andSampleIdLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("sample_id", value, ConditionMode.LESS_EQUAL, "sampleId", "java.lang.Integer", "Float");
          return this;
      }

      public AtBotanyCriteria andSampleIdBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("sample_id", value1, value2, ConditionMode.BETWEEN, "sampleId", "java.lang.Integer", "Float");
    	  return this;
      }

      public AtBotanyCriteria andSampleIdNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("sample_id", value1, value2, ConditionMode.NOT_BETWEEN, "sampleId", "java.lang.Integer", "Float");
          return this;
      }
        
      public AtBotanyCriteria andSampleIdIn(List<java.lang.Integer> values) {
          addCriterion("sample_id", values, ConditionMode.IN, "sampleId", "java.lang.Integer", "Float");
          return this;
      }

      public AtBotanyCriteria andSampleIdNotIn(List<java.lang.Integer> values) {
          addCriterion("sample_id", values, ConditionMode.NOT_IN, "sampleId", "java.lang.Integer", "Float");
          return this;
      }
}