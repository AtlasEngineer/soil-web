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
public class SpeciesPestsCriteria extends Columns implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static SpeciesPestsCriteria create() {
		return new SpeciesPestsCriteria();
	}
	
	public static SpeciesPestsCriteria create(Column column) {
		SpeciesPestsCriteria that = new SpeciesPestsCriteria();
		that.add(column);
        return that;
    }

    public static SpeciesPestsCriteria create(String name, Object value) {
        return (SpeciesPestsCriteria) create().eq(name, value);
    }
    
    public Example example() {
    	return Example.create("at_species_pests", this);
    }
    
    /**
     * equals
     *
     * @param name
     * @param value
     * @return
     */
    public SpeciesPestsCriteria eq(String name, Object value) {
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
    public SpeciesPestsCriteria ne(String name, Object value) {
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

    public SpeciesPestsCriteria like(String name, Object value) {
    	super.like(name, value);
        return this;
    }
    
    public SpeciesPestsCriteria notLike(String name, Object value) {
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
    public SpeciesPestsCriteria gt(String name, Object value) {
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
    public SpeciesPestsCriteria ge(String name, Object value) {
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
    public SpeciesPestsCriteria lt(String name, Object value) {
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
    public SpeciesPestsCriteria le(String name, Object value) {
    	super.le(name, value);
        return this;
    }
    
    public SpeciesPestsCriteria isnull(String name) {
    	super.isnull(name);
        return this;
    } 

    public SpeciesPestsCriteria notNull(String name) {
    	super.notNull(name);
        return this;
    } 
    
    public SpeciesPestsCriteria empty(String name) {
    	super.empty(name);
        return this;
    } 
    
    public SpeciesPestsCriteria notEmpty(String name) {
    	super.notEmpty(name);
        return this;
    } 
    
    public SpeciesPestsCriteria add(Column column) {
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
		 
	public SpeciesPestsCriteria andIdIsNull() {
		isnull("id");
		return this;
	}
	
	public SpeciesPestsCriteria andIdIsNotNull() {
		notNull("id");
		return this;
	}
	
	public SpeciesPestsCriteria andIdIsEmpty() {
		empty("id");
		return this;
	}

	public SpeciesPestsCriteria andIdIsNotEmpty() {
		notEmpty("id");
		return this;
	}
       public SpeciesPestsCriteria andIdEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public SpeciesPestsCriteria andIdNotEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.NOT_EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public SpeciesPestsCriteria andIdGreaterThan(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.GREATER_THEN, "id", "java.lang.Integer", "Float");
          return this;
      }

      public SpeciesPestsCriteria andIdGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.GREATER_EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public SpeciesPestsCriteria andIdLessThan(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.LESS_THEN, "id", "java.lang.Integer", "Float");
          return this;
      }

      public SpeciesPestsCriteria andIdLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.LESS_EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public SpeciesPestsCriteria andIdBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("id", value1, value2, ConditionMode.BETWEEN, "id", "java.lang.Integer", "Float");
    	  return this;
      }

      public SpeciesPestsCriteria andIdNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("id", value1, value2, ConditionMode.NOT_BETWEEN, "id", "java.lang.Integer", "Float");
          return this;
      }
        
      public SpeciesPestsCriteria andIdIn(List<java.lang.Integer> values) {
          addCriterion("id", values, ConditionMode.IN, "id", "java.lang.Integer", "Float");
          return this;
      }

      public SpeciesPestsCriteria andIdNotIn(List<java.lang.Integer> values) {
          addCriterion("id", values, ConditionMode.NOT_IN, "id", "java.lang.Integer", "Float");
          return this;
      }
	public SpeciesPestsCriteria andDelIsNull() {
		isnull("del");
		return this;
	}
	
	public SpeciesPestsCriteria andDelIsNotNull() {
		notNull("del");
		return this;
	}
	
	public SpeciesPestsCriteria andDelIsEmpty() {
		empty("del");
		return this;
	}

	public SpeciesPestsCriteria andDelIsNotEmpty() {
		notEmpty("del");
		return this;
	}
       public SpeciesPestsCriteria andDelEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public SpeciesPestsCriteria andDelNotEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.NOT_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public SpeciesPestsCriteria andDelGreaterThan(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.GREATER_THEN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public SpeciesPestsCriteria andDelGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.GREATER_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public SpeciesPestsCriteria andDelLessThan(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.LESS_THEN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public SpeciesPestsCriteria andDelLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.LESS_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public SpeciesPestsCriteria andDelBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("del", value1, value2, ConditionMode.BETWEEN, "del", "java.lang.Integer", "Float");
    	  return this;
      }

      public SpeciesPestsCriteria andDelNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("del", value1, value2, ConditionMode.NOT_BETWEEN, "del", "java.lang.Integer", "Float");
          return this;
      }
        
      public SpeciesPestsCriteria andDelIn(List<java.lang.Integer> values) {
          addCriterion("del", values, ConditionMode.IN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public SpeciesPestsCriteria andDelNotIn(List<java.lang.Integer> values) {
          addCriterion("del", values, ConditionMode.NOT_IN, "del", "java.lang.Integer", "Float");
          return this;
      }
	public SpeciesPestsCriteria andPestsIdIsNull() {
		isnull("pests_id");
		return this;
	}
	
	public SpeciesPestsCriteria andPestsIdIsNotNull() {
		notNull("pests_id");
		return this;
	}
	
	public SpeciesPestsCriteria andPestsIdIsEmpty() {
		empty("pests_id");
		return this;
	}

	public SpeciesPestsCriteria andPestsIdIsNotEmpty() {
		notEmpty("pests_id");
		return this;
	}
       public SpeciesPestsCriteria andPestsIdEqualTo(java.lang.Integer value) {
          addCriterion("pests_id", value, ConditionMode.EQUAL, "pestsId", "java.lang.Integer", "Float");
          return this;
      }

      public SpeciesPestsCriteria andPestsIdNotEqualTo(java.lang.Integer value) {
          addCriterion("pests_id", value, ConditionMode.NOT_EQUAL, "pestsId", "java.lang.Integer", "Float");
          return this;
      }

      public SpeciesPestsCriteria andPestsIdGreaterThan(java.lang.Integer value) {
          addCriterion("pests_id", value, ConditionMode.GREATER_THEN, "pestsId", "java.lang.Integer", "Float");
          return this;
      }

      public SpeciesPestsCriteria andPestsIdGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("pests_id", value, ConditionMode.GREATER_EQUAL, "pestsId", "java.lang.Integer", "Float");
          return this;
      }

      public SpeciesPestsCriteria andPestsIdLessThan(java.lang.Integer value) {
          addCriterion("pests_id", value, ConditionMode.LESS_THEN, "pestsId", "java.lang.Integer", "Float");
          return this;
      }

      public SpeciesPestsCriteria andPestsIdLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("pests_id", value, ConditionMode.LESS_EQUAL, "pestsId", "java.lang.Integer", "Float");
          return this;
      }

      public SpeciesPestsCriteria andPestsIdBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("pests_id", value1, value2, ConditionMode.BETWEEN, "pestsId", "java.lang.Integer", "Float");
    	  return this;
      }

      public SpeciesPestsCriteria andPestsIdNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("pests_id", value1, value2, ConditionMode.NOT_BETWEEN, "pestsId", "java.lang.Integer", "Float");
          return this;
      }
        
      public SpeciesPestsCriteria andPestsIdIn(List<java.lang.Integer> values) {
          addCriterion("pests_id", values, ConditionMode.IN, "pestsId", "java.lang.Integer", "Float");
          return this;
      }

      public SpeciesPestsCriteria andPestsIdNotIn(List<java.lang.Integer> values) {
          addCriterion("pests_id", values, ConditionMode.NOT_IN, "pestsId", "java.lang.Integer", "Float");
          return this;
      }
	public SpeciesPestsCriteria andSpeciesIdIsNull() {
		isnull("species_id");
		return this;
	}
	
	public SpeciesPestsCriteria andSpeciesIdIsNotNull() {
		notNull("species_id");
		return this;
	}
	
	public SpeciesPestsCriteria andSpeciesIdIsEmpty() {
		empty("species_id");
		return this;
	}

	public SpeciesPestsCriteria andSpeciesIdIsNotEmpty() {
		notEmpty("species_id");
		return this;
	}
       public SpeciesPestsCriteria andSpeciesIdEqualTo(java.lang.Integer value) {
          addCriterion("species_id", value, ConditionMode.EQUAL, "speciesId", "java.lang.Integer", "Float");
          return this;
      }

      public SpeciesPestsCriteria andSpeciesIdNotEqualTo(java.lang.Integer value) {
          addCriterion("species_id", value, ConditionMode.NOT_EQUAL, "speciesId", "java.lang.Integer", "Float");
          return this;
      }

      public SpeciesPestsCriteria andSpeciesIdGreaterThan(java.lang.Integer value) {
          addCriterion("species_id", value, ConditionMode.GREATER_THEN, "speciesId", "java.lang.Integer", "Float");
          return this;
      }

      public SpeciesPestsCriteria andSpeciesIdGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("species_id", value, ConditionMode.GREATER_EQUAL, "speciesId", "java.lang.Integer", "Float");
          return this;
      }

      public SpeciesPestsCriteria andSpeciesIdLessThan(java.lang.Integer value) {
          addCriterion("species_id", value, ConditionMode.LESS_THEN, "speciesId", "java.lang.Integer", "Float");
          return this;
      }

      public SpeciesPestsCriteria andSpeciesIdLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("species_id", value, ConditionMode.LESS_EQUAL, "speciesId", "java.lang.Integer", "Float");
          return this;
      }

      public SpeciesPestsCriteria andSpeciesIdBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("species_id", value1, value2, ConditionMode.BETWEEN, "speciesId", "java.lang.Integer", "Float");
    	  return this;
      }

      public SpeciesPestsCriteria andSpeciesIdNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("species_id", value1, value2, ConditionMode.NOT_BETWEEN, "speciesId", "java.lang.Integer", "Float");
          return this;
      }
        
      public SpeciesPestsCriteria andSpeciesIdIn(List<java.lang.Integer> values) {
          addCriterion("species_id", values, ConditionMode.IN, "speciesId", "java.lang.Integer", "Float");
          return this;
      }

      public SpeciesPestsCriteria andSpeciesIdNotIn(List<java.lang.Integer> values) {
          addCriterion("species_id", values, ConditionMode.NOT_IN, "speciesId", "java.lang.Integer", "Float");
          return this;
      }
}