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
public class TiankuaiCriteria extends Columns implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static TiankuaiCriteria create() {
		return new TiankuaiCriteria();
	}
	
	public static TiankuaiCriteria create(Column column) {
		TiankuaiCriteria that = new TiankuaiCriteria();
		that.add(column);
        return that;
    }

    public static TiankuaiCriteria create(String name, Object value) {
        return (TiankuaiCriteria) create().eq(name, value);
    }
    
    public Example example() {
    	return Example.create("tr_tiankuai", this);
    }
    
    /**
     * equals
     *
     * @param name
     * @param value
     * @return
     */
    public TiankuaiCriteria eq(String name, Object value) {
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
    public TiankuaiCriteria ne(String name, Object value) {
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

    public TiankuaiCriteria like(String name, Object value) {
    	super.like(name, value);
        return this;
    }
    
    public TiankuaiCriteria notLike(String name, Object value) {
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
    public TiankuaiCriteria gt(String name, Object value) {
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
    public TiankuaiCriteria ge(String name, Object value) {
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
    public TiankuaiCriteria lt(String name, Object value) {
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
    public TiankuaiCriteria le(String name, Object value) {
    	super.le(name, value);
        return this;
    }
    
    public TiankuaiCriteria isnull(String name) {
    	super.isnull(name);
        return this;
    } 

    public TiankuaiCriteria notNull(String name) {
    	super.notNull(name);
        return this;
    } 
    
    public TiankuaiCriteria empty(String name) {
    	super.empty(name);
        return this;
    } 
    
    public TiankuaiCriteria notEmpty(String name) {
    	super.notEmpty(name);
        return this;
    } 
    
    public TiankuaiCriteria add(Column column) {
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
		 
	public TiankuaiCriteria andGidIsNull() {
		isnull("gid");
		return this;
	}
	
	public TiankuaiCriteria andGidIsNotNull() {
		notNull("gid");
		return this;
	}
	
	public TiankuaiCriteria andGidIsEmpty() {
		empty("gid");
		return this;
	}

	public TiankuaiCriteria andGidIsNotEmpty() {
		notEmpty("gid");
		return this;
	}
       public TiankuaiCriteria andGidEqualTo(java.lang.Integer value) {
          addCriterion("gid", value, ConditionMode.EQUAL, "gid", "java.lang.Integer", "Float");
          return this;
      }

      public TiankuaiCriteria andGidNotEqualTo(java.lang.Integer value) {
          addCriterion("gid", value, ConditionMode.NOT_EQUAL, "gid", "java.lang.Integer", "Float");
          return this;
      }

      public TiankuaiCriteria andGidGreaterThan(java.lang.Integer value) {
          addCriterion("gid", value, ConditionMode.GREATER_THEN, "gid", "java.lang.Integer", "Float");
          return this;
      }

      public TiankuaiCriteria andGidGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("gid", value, ConditionMode.GREATER_EQUAL, "gid", "java.lang.Integer", "Float");
          return this;
      }

      public TiankuaiCriteria andGidLessThan(java.lang.Integer value) {
          addCriterion("gid", value, ConditionMode.LESS_THEN, "gid", "java.lang.Integer", "Float");
          return this;
      }

      public TiankuaiCriteria andGidLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("gid", value, ConditionMode.LESS_EQUAL, "gid", "java.lang.Integer", "Float");
          return this;
      }

      public TiankuaiCriteria andGidBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("gid", value1, value2, ConditionMode.BETWEEN, "gid", "java.lang.Integer", "Float");
    	  return this;
      }

      public TiankuaiCriteria andGidNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("gid", value1, value2, ConditionMode.NOT_BETWEEN, "gid", "java.lang.Integer", "Float");
          return this;
      }
        
      public TiankuaiCriteria andGidIn(List<java.lang.Integer> values) {
          addCriterion("gid", values, ConditionMode.IN, "gid", "java.lang.Integer", "Float");
          return this;
      }

      public TiankuaiCriteria andGidNotIn(List<java.lang.Integer> values) {
          addCriterion("gid", values, ConditionMode.NOT_IN, "gid", "java.lang.Integer", "Float");
          return this;
      }
	public TiankuaiCriteria andNameIsNull() {
		isnull("name");
		return this;
	}
	
	public TiankuaiCriteria andNameIsNotNull() {
		notNull("name");
		return this;
	}
	
	public TiankuaiCriteria andNameIsEmpty() {
		empty("name");
		return this;
	}

	public TiankuaiCriteria andNameIsNotEmpty() {
		notEmpty("name");
		return this;
	}
        public TiankuaiCriteria andNameLike(java.lang.String value) {
    	   addCriterion("name", value, ConditionMode.FUZZY, "name", "java.lang.String", "Float");
    	   return this;
      }

      public TiankuaiCriteria andNameNotLike(java.lang.String value) {
          addCriterion("name", value, ConditionMode.NOT_FUZZY, "name", "java.lang.String", "Float");
          return this;
      }
      public TiankuaiCriteria andNameEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andNameNotEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.NOT_EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andNameGreaterThan(java.lang.String value) {
          addCriterion("name", value, ConditionMode.GREATER_THEN, "name", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andNameGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.GREATER_EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andNameLessThan(java.lang.String value) {
          addCriterion("name", value, ConditionMode.LESS_THEN, "name", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andNameLessThanOrEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.LESS_EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andNameBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("name", value1, value2, ConditionMode.BETWEEN, "name", "java.lang.String", "String");
    	  return this;
      }

      public TiankuaiCriteria andNameNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("name", value1, value2, ConditionMode.NOT_BETWEEN, "name", "java.lang.String", "String");
          return this;
      }
        
      public TiankuaiCriteria andNameIn(List<java.lang.String> values) {
          addCriterion("name", values, ConditionMode.IN, "name", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andNameNotIn(List<java.lang.String> values) {
          addCriterion("name", values, ConditionMode.NOT_IN, "name", "java.lang.String", "String");
          return this;
      }
	public TiankuaiCriteria andLayerIsNull() {
		isnull("layer");
		return this;
	}
	
	public TiankuaiCriteria andLayerIsNotNull() {
		notNull("layer");
		return this;
	}
	
	public TiankuaiCriteria andLayerIsEmpty() {
		empty("layer");
		return this;
	}

	public TiankuaiCriteria andLayerIsNotEmpty() {
		notEmpty("layer");
		return this;
	}
        public TiankuaiCriteria andLayerLike(java.lang.String value) {
    	   addCriterion("layer", value, ConditionMode.FUZZY, "layer", "java.lang.String", "String");
    	   return this;
      }

      public TiankuaiCriteria andLayerNotLike(java.lang.String value) {
          addCriterion("layer", value, ConditionMode.NOT_FUZZY, "layer", "java.lang.String", "String");
          return this;
      }
      public TiankuaiCriteria andLayerEqualTo(java.lang.String value) {
          addCriterion("layer", value, ConditionMode.EQUAL, "layer", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andLayerNotEqualTo(java.lang.String value) {
          addCriterion("layer", value, ConditionMode.NOT_EQUAL, "layer", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andLayerGreaterThan(java.lang.String value) {
          addCriterion("layer", value, ConditionMode.GREATER_THEN, "layer", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andLayerGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("layer", value, ConditionMode.GREATER_EQUAL, "layer", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andLayerLessThan(java.lang.String value) {
          addCriterion("layer", value, ConditionMode.LESS_THEN, "layer", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andLayerLessThanOrEqualTo(java.lang.String value) {
          addCriterion("layer", value, ConditionMode.LESS_EQUAL, "layer", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andLayerBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("layer", value1, value2, ConditionMode.BETWEEN, "layer", "java.lang.String", "String");
    	  return this;
      }

      public TiankuaiCriteria andLayerNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("layer", value1, value2, ConditionMode.NOT_BETWEEN, "layer", "java.lang.String", "String");
          return this;
      }
        
      public TiankuaiCriteria andLayerIn(List<java.lang.String> values) {
          addCriterion("layer", values, ConditionMode.IN, "layer", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andLayerNotIn(List<java.lang.String> values) {
          addCriterion("layer", values, ConditionMode.NOT_IN, "layer", "java.lang.String", "String");
          return this;
      }
	public TiankuaiCriteria andAreaIsNull() {
		isnull("area");
		return this;
	}
	
	public TiankuaiCriteria andAreaIsNotNull() {
		notNull("area");
		return this;
	}
	
	public TiankuaiCriteria andAreaIsEmpty() {
		empty("area");
		return this;
	}

	public TiankuaiCriteria andAreaIsNotEmpty() {
		notEmpty("area");
		return this;
	}
       public TiankuaiCriteria andAreaEqualTo(java.math.BigDecimal value) {
          addCriterion("area", value, ConditionMode.EQUAL, "area", "java.math.BigDecimal", "Float");
          return this;
      }

      public TiankuaiCriteria andAreaNotEqualTo(java.math.BigDecimal value) {
          addCriterion("area", value, ConditionMode.NOT_EQUAL, "area", "java.math.BigDecimal", "Float");
          return this;
      }

      public TiankuaiCriteria andAreaGreaterThan(java.math.BigDecimal value) {
          addCriterion("area", value, ConditionMode.GREATER_THEN, "area", "java.math.BigDecimal", "Float");
          return this;
      }

      public TiankuaiCriteria andAreaGreaterThanOrEqualTo(java.math.BigDecimal value) {
          addCriterion("area", value, ConditionMode.GREATER_EQUAL, "area", "java.math.BigDecimal", "Float");
          return this;
      }

      public TiankuaiCriteria andAreaLessThan(java.math.BigDecimal value) {
          addCriterion("area", value, ConditionMode.LESS_THEN, "area", "java.math.BigDecimal", "Float");
          return this;
      }

      public TiankuaiCriteria andAreaLessThanOrEqualTo(java.math.BigDecimal value) {
          addCriterion("area", value, ConditionMode.LESS_EQUAL, "area", "java.math.BigDecimal", "Float");
          return this;
      }

      public TiankuaiCriteria andAreaBetween(java.math.BigDecimal value1, java.math.BigDecimal value2) {
    	  addCriterion("area", value1, value2, ConditionMode.BETWEEN, "area", "java.math.BigDecimal", "Float");
    	  return this;
      }

      public TiankuaiCriteria andAreaNotBetween(java.math.BigDecimal value1, java.math.BigDecimal value2) {
          addCriterion("area", value1, value2, ConditionMode.NOT_BETWEEN, "area", "java.math.BigDecimal", "Float");
          return this;
      }
        
      public TiankuaiCriteria andAreaIn(List<java.math.BigDecimal> values) {
          addCriterion("area", values, ConditionMode.IN, "area", "java.math.BigDecimal", "Float");
          return this;
      }

      public TiankuaiCriteria andAreaNotIn(List<java.math.BigDecimal> values) {
          addCriterion("area", values, ConditionMode.NOT_IN, "area", "java.math.BigDecimal", "Float");
          return this;
      }
	public TiankuaiCriteria andOidIsNull() {
		isnull("oid_");
		return this;
	}
	
	public TiankuaiCriteria andOidIsNotNull() {
		notNull("oid_");
		return this;
	}
	
	public TiankuaiCriteria andOidIsEmpty() {
		empty("oid_");
		return this;
	}

	public TiankuaiCriteria andOidIsNotEmpty() {
		notEmpty("oid_");
		return this;
	}
       public TiankuaiCriteria andOidEqualTo(java.lang.Integer value) {
          addCriterion("oid_", value, ConditionMode.EQUAL, "oid", "java.lang.Integer", "Float");
          return this;
      }

      public TiankuaiCriteria andOidNotEqualTo(java.lang.Integer value) {
          addCriterion("oid_", value, ConditionMode.NOT_EQUAL, "oid", "java.lang.Integer", "Float");
          return this;
      }

      public TiankuaiCriteria andOidGreaterThan(java.lang.Integer value) {
          addCriterion("oid_", value, ConditionMode.GREATER_THEN, "oid", "java.lang.Integer", "Float");
          return this;
      }

      public TiankuaiCriteria andOidGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("oid_", value, ConditionMode.GREATER_EQUAL, "oid", "java.lang.Integer", "Float");
          return this;
      }

      public TiankuaiCriteria andOidLessThan(java.lang.Integer value) {
          addCriterion("oid_", value, ConditionMode.LESS_THEN, "oid", "java.lang.Integer", "Float");
          return this;
      }

      public TiankuaiCriteria andOidLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("oid_", value, ConditionMode.LESS_EQUAL, "oid", "java.lang.Integer", "Float");
          return this;
      }

      public TiankuaiCriteria andOidBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("oid_", value1, value2, ConditionMode.BETWEEN, "oid", "java.lang.Integer", "Float");
    	  return this;
      }

      public TiankuaiCriteria andOidNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("oid_", value1, value2, ConditionMode.NOT_BETWEEN, "oid", "java.lang.Integer", "Float");
          return this;
      }
        
      public TiankuaiCriteria andOidIn(List<java.lang.Integer> values) {
          addCriterion("oid_", values, ConditionMode.IN, "oid", "java.lang.Integer", "Float");
          return this;
      }

      public TiankuaiCriteria andOidNotIn(List<java.lang.Integer> values) {
          addCriterion("oid_", values, ConditionMode.NOT_IN, "oid", "java.lang.Integer", "Float");
          return this;
      }
	public TiankuaiCriteria andShapeAreaIsNull() {
		isnull("shape_area");
		return this;
	}
	
	public TiankuaiCriteria andShapeAreaIsNotNull() {
		notNull("shape_area");
		return this;
	}
	
	public TiankuaiCriteria andShapeAreaIsEmpty() {
		empty("shape_area");
		return this;
	}

	public TiankuaiCriteria andShapeAreaIsNotEmpty() {
		notEmpty("shape_area");
		return this;
	}
       public TiankuaiCriteria andShapeAreaEqualTo(java.math.BigDecimal value) {
          addCriterion("shape_area", value, ConditionMode.EQUAL, "shapeArea", "java.math.BigDecimal", "Float");
          return this;
      }

      public TiankuaiCriteria andShapeAreaNotEqualTo(java.math.BigDecimal value) {
          addCriterion("shape_area", value, ConditionMode.NOT_EQUAL, "shapeArea", "java.math.BigDecimal", "Float");
          return this;
      }

      public TiankuaiCriteria andShapeAreaGreaterThan(java.math.BigDecimal value) {
          addCriterion("shape_area", value, ConditionMode.GREATER_THEN, "shapeArea", "java.math.BigDecimal", "Float");
          return this;
      }

      public TiankuaiCriteria andShapeAreaGreaterThanOrEqualTo(java.math.BigDecimal value) {
          addCriterion("shape_area", value, ConditionMode.GREATER_EQUAL, "shapeArea", "java.math.BigDecimal", "Float");
          return this;
      }

      public TiankuaiCriteria andShapeAreaLessThan(java.math.BigDecimal value) {
          addCriterion("shape_area", value, ConditionMode.LESS_THEN, "shapeArea", "java.math.BigDecimal", "Float");
          return this;
      }

      public TiankuaiCriteria andShapeAreaLessThanOrEqualTo(java.math.BigDecimal value) {
          addCriterion("shape_area", value, ConditionMode.LESS_EQUAL, "shapeArea", "java.math.BigDecimal", "Float");
          return this;
      }

      public TiankuaiCriteria andShapeAreaBetween(java.math.BigDecimal value1, java.math.BigDecimal value2) {
    	  addCriterion("shape_area", value1, value2, ConditionMode.BETWEEN, "shapeArea", "java.math.BigDecimal", "Float");
    	  return this;
      }

      public TiankuaiCriteria andShapeAreaNotBetween(java.math.BigDecimal value1, java.math.BigDecimal value2) {
          addCriterion("shape_area", value1, value2, ConditionMode.NOT_BETWEEN, "shapeArea", "java.math.BigDecimal", "Float");
          return this;
      }
        
      public TiankuaiCriteria andShapeAreaIn(List<java.math.BigDecimal> values) {
          addCriterion("shape_area", values, ConditionMode.IN, "shapeArea", "java.math.BigDecimal", "Float");
          return this;
      }

      public TiankuaiCriteria andShapeAreaNotIn(List<java.math.BigDecimal> values) {
          addCriterion("shape_area", values, ConditionMode.NOT_IN, "shapeArea", "java.math.BigDecimal", "Float");
          return this;
      }
	public TiankuaiCriteria andIdIsNull() {
		isnull("id");
		return this;
	}
	
	public TiankuaiCriteria andIdIsNotNull() {
		notNull("id");
		return this;
	}
	
	public TiankuaiCriteria andIdIsEmpty() {
		empty("id");
		return this;
	}

	public TiankuaiCriteria andIdIsNotEmpty() {
		notEmpty("id");
		return this;
	}
       public TiankuaiCriteria andIdEqualTo(java.math.BigDecimal value) {
          addCriterion("id", value, ConditionMode.EQUAL, "id", "java.math.BigDecimal", "Float");
          return this;
      }

      public TiankuaiCriteria andIdNotEqualTo(java.math.BigDecimal value) {
          addCriterion("id", value, ConditionMode.NOT_EQUAL, "id", "java.math.BigDecimal", "Float");
          return this;
      }

      public TiankuaiCriteria andIdGreaterThan(java.math.BigDecimal value) {
          addCriterion("id", value, ConditionMode.GREATER_THEN, "id", "java.math.BigDecimal", "Float");
          return this;
      }

      public TiankuaiCriteria andIdGreaterThanOrEqualTo(java.math.BigDecimal value) {
          addCriterion("id", value, ConditionMode.GREATER_EQUAL, "id", "java.math.BigDecimal", "Float");
          return this;
      }

      public TiankuaiCriteria andIdLessThan(java.math.BigDecimal value) {
          addCriterion("id", value, ConditionMode.LESS_THEN, "id", "java.math.BigDecimal", "Float");
          return this;
      }

      public TiankuaiCriteria andIdLessThanOrEqualTo(java.math.BigDecimal value) {
          addCriterion("id", value, ConditionMode.LESS_EQUAL, "id", "java.math.BigDecimal", "Float");
          return this;
      }

      public TiankuaiCriteria andIdBetween(java.math.BigDecimal value1, java.math.BigDecimal value2) {
    	  addCriterion("id", value1, value2, ConditionMode.BETWEEN, "id", "java.math.BigDecimal", "Float");
    	  return this;
      }

      public TiankuaiCriteria andIdNotBetween(java.math.BigDecimal value1, java.math.BigDecimal value2) {
          addCriterion("id", value1, value2, ConditionMode.NOT_BETWEEN, "id", "java.math.BigDecimal", "Float");
          return this;
      }
        
      public TiankuaiCriteria andIdIn(List<java.math.BigDecimal> values) {
          addCriterion("id", values, ConditionMode.IN, "id", "java.math.BigDecimal", "Float");
          return this;
      }

      public TiankuaiCriteria andIdNotIn(List<java.math.BigDecimal> values) {
          addCriterion("id", values, ConditionMode.NOT_IN, "id", "java.math.BigDecimal", "Float");
          return this;
      }
	public TiankuaiCriteria andGeomIsNull() {
		isnull("geom");
		return this;
	}
	
	public TiankuaiCriteria andGeomIsNotNull() {
		notNull("geom");
		return this;
	}
	
	public TiankuaiCriteria andGeomIsEmpty() {
		empty("geom");
		return this;
	}

	public TiankuaiCriteria andGeomIsNotEmpty() {
		notEmpty("geom");
		return this;
	}
        public TiankuaiCriteria andGeomLike(java.lang.String value) {
    	   addCriterion("geom", value, ConditionMode.FUZZY, "geom", "java.lang.String", "Float");
    	   return this;
      }

      public TiankuaiCriteria andGeomNotLike(java.lang.String value) {
          addCriterion("geom", value, ConditionMode.NOT_FUZZY, "geom", "java.lang.String", "Float");
          return this;
      }
      public TiankuaiCriteria andGeomEqualTo(java.lang.String value) {
          addCriterion("geom", value, ConditionMode.EQUAL, "geom", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andGeomNotEqualTo(java.lang.String value) {
          addCriterion("geom", value, ConditionMode.NOT_EQUAL, "geom", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andGeomGreaterThan(java.lang.String value) {
          addCriterion("geom", value, ConditionMode.GREATER_THEN, "geom", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andGeomGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("geom", value, ConditionMode.GREATER_EQUAL, "geom", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andGeomLessThan(java.lang.String value) {
          addCriterion("geom", value, ConditionMode.LESS_THEN, "geom", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andGeomLessThanOrEqualTo(java.lang.String value) {
          addCriterion("geom", value, ConditionMode.LESS_EQUAL, "geom", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andGeomBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("geom", value1, value2, ConditionMode.BETWEEN, "geom", "java.lang.String", "String");
    	  return this;
      }

      public TiankuaiCriteria andGeomNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("geom", value1, value2, ConditionMode.NOT_BETWEEN, "geom", "java.lang.String", "String");
          return this;
      }
        
      public TiankuaiCriteria andGeomIn(List<java.lang.String> values) {
          addCriterion("geom", values, ConditionMode.IN, "geom", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andGeomNotIn(List<java.lang.String> values) {
          addCriterion("geom", values, ConditionMode.NOT_IN, "geom", "java.lang.String", "String");
          return this;
      }
	public TiankuaiCriteria andTypeIsNull() {
		isnull("type");
		return this;
	}
	
	public TiankuaiCriteria andTypeIsNotNull() {
		notNull("type");
		return this;
	}
	
	public TiankuaiCriteria andTypeIsEmpty() {
		empty("type");
		return this;
	}

	public TiankuaiCriteria andTypeIsNotEmpty() {
		notEmpty("type");
		return this;
	}
        public TiankuaiCriteria andTypeLike(java.lang.String value) {
    	   addCriterion("type", value, ConditionMode.FUZZY, "type", "java.lang.String", "String");
    	   return this;
      }

      public TiankuaiCriteria andTypeNotLike(java.lang.String value) {
          addCriterion("type", value, ConditionMode.NOT_FUZZY, "type", "java.lang.String", "String");
          return this;
      }
      public TiankuaiCriteria andTypeEqualTo(java.lang.String value) {
          addCriterion("type", value, ConditionMode.EQUAL, "type", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andTypeNotEqualTo(java.lang.String value) {
          addCriterion("type", value, ConditionMode.NOT_EQUAL, "type", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andTypeGreaterThan(java.lang.String value) {
          addCriterion("type", value, ConditionMode.GREATER_THEN, "type", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andTypeGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("type", value, ConditionMode.GREATER_EQUAL, "type", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andTypeLessThan(java.lang.String value) {
          addCriterion("type", value, ConditionMode.LESS_THEN, "type", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andTypeLessThanOrEqualTo(java.lang.String value) {
          addCriterion("type", value, ConditionMode.LESS_EQUAL, "type", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andTypeBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("type", value1, value2, ConditionMode.BETWEEN, "type", "java.lang.String", "String");
    	  return this;
      }

      public TiankuaiCriteria andTypeNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("type", value1, value2, ConditionMode.NOT_BETWEEN, "type", "java.lang.String", "String");
          return this;
      }
        
      public TiankuaiCriteria andTypeIn(List<java.lang.String> values) {
          addCriterion("type", values, ConditionMode.IN, "type", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andTypeNotIn(List<java.lang.String> values) {
          addCriterion("type", values, ConditionMode.NOT_IN, "type", "java.lang.String", "String");
          return this;
      }
	public TiankuaiCriteria andDkNameIsNull() {
		isnull("dk_name");
		return this;
	}
	
	public TiankuaiCriteria andDkNameIsNotNull() {
		notNull("dk_name");
		return this;
	}
	
	public TiankuaiCriteria andDkNameIsEmpty() {
		empty("dk_name");
		return this;
	}

	public TiankuaiCriteria andDkNameIsNotEmpty() {
		notEmpty("dk_name");
		return this;
	}
        public TiankuaiCriteria andDkNameLike(java.lang.String value) {
    	   addCriterion("dk_name", value, ConditionMode.FUZZY, "dkName", "java.lang.String", "String");
    	   return this;
      }

      public TiankuaiCriteria andDkNameNotLike(java.lang.String value) {
          addCriterion("dk_name", value, ConditionMode.NOT_FUZZY, "dkName", "java.lang.String", "String");
          return this;
      }
      public TiankuaiCriteria andDkNameEqualTo(java.lang.String value) {
          addCriterion("dk_name", value, ConditionMode.EQUAL, "dkName", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkNameNotEqualTo(java.lang.String value) {
          addCriterion("dk_name", value, ConditionMode.NOT_EQUAL, "dkName", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkNameGreaterThan(java.lang.String value) {
          addCriterion("dk_name", value, ConditionMode.GREATER_THEN, "dkName", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkNameGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("dk_name", value, ConditionMode.GREATER_EQUAL, "dkName", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkNameLessThan(java.lang.String value) {
          addCriterion("dk_name", value, ConditionMode.LESS_THEN, "dkName", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkNameLessThanOrEqualTo(java.lang.String value) {
          addCriterion("dk_name", value, ConditionMode.LESS_EQUAL, "dkName", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkNameBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("dk_name", value1, value2, ConditionMode.BETWEEN, "dkName", "java.lang.String", "String");
    	  return this;
      }

      public TiankuaiCriteria andDkNameNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("dk_name", value1, value2, ConditionMode.NOT_BETWEEN, "dkName", "java.lang.String", "String");
          return this;
      }
        
      public TiankuaiCriteria andDkNameIn(List<java.lang.String> values) {
          addCriterion("dk_name", values, ConditionMode.IN, "dkName", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkNameNotIn(List<java.lang.String> values) {
          addCriterion("dk_name", values, ConditionMode.NOT_IN, "dkName", "java.lang.String", "String");
          return this;
      }
	public TiankuaiCriteria andDkAddressIsNull() {
		isnull("dk_address");
		return this;
	}
	
	public TiankuaiCriteria andDkAddressIsNotNull() {
		notNull("dk_address");
		return this;
	}
	
	public TiankuaiCriteria andDkAddressIsEmpty() {
		empty("dk_address");
		return this;
	}

	public TiankuaiCriteria andDkAddressIsNotEmpty() {
		notEmpty("dk_address");
		return this;
	}
        public TiankuaiCriteria andDkAddressLike(java.lang.String value) {
    	   addCriterion("dk_address", value, ConditionMode.FUZZY, "dkAddress", "java.lang.String", "String");
    	   return this;
      }

      public TiankuaiCriteria andDkAddressNotLike(java.lang.String value) {
          addCriterion("dk_address", value, ConditionMode.NOT_FUZZY, "dkAddress", "java.lang.String", "String");
          return this;
      }
      public TiankuaiCriteria andDkAddressEqualTo(java.lang.String value) {
          addCriterion("dk_address", value, ConditionMode.EQUAL, "dkAddress", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkAddressNotEqualTo(java.lang.String value) {
          addCriterion("dk_address", value, ConditionMode.NOT_EQUAL, "dkAddress", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkAddressGreaterThan(java.lang.String value) {
          addCriterion("dk_address", value, ConditionMode.GREATER_THEN, "dkAddress", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkAddressGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("dk_address", value, ConditionMode.GREATER_EQUAL, "dkAddress", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkAddressLessThan(java.lang.String value) {
          addCriterion("dk_address", value, ConditionMode.LESS_THEN, "dkAddress", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkAddressLessThanOrEqualTo(java.lang.String value) {
          addCriterion("dk_address", value, ConditionMode.LESS_EQUAL, "dkAddress", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkAddressBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("dk_address", value1, value2, ConditionMode.BETWEEN, "dkAddress", "java.lang.String", "String");
    	  return this;
      }

      public TiankuaiCriteria andDkAddressNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("dk_address", value1, value2, ConditionMode.NOT_BETWEEN, "dkAddress", "java.lang.String", "String");
          return this;
      }
        
      public TiankuaiCriteria andDkAddressIn(List<java.lang.String> values) {
          addCriterion("dk_address", values, ConditionMode.IN, "dkAddress", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkAddressNotIn(List<java.lang.String> values) {
          addCriterion("dk_address", values, ConditionMode.NOT_IN, "dkAddress", "java.lang.String", "String");
          return this;
      }
	public TiankuaiCriteria andDkUrlIsNull() {
		isnull("dk_url");
		return this;
	}
	
	public TiankuaiCriteria andDkUrlIsNotNull() {
		notNull("dk_url");
		return this;
	}
	
	public TiankuaiCriteria andDkUrlIsEmpty() {
		empty("dk_url");
		return this;
	}

	public TiankuaiCriteria andDkUrlIsNotEmpty() {
		notEmpty("dk_url");
		return this;
	}
        public TiankuaiCriteria andDkUrlLike(java.lang.String value) {
    	   addCriterion("dk_url", value, ConditionMode.FUZZY, "dkUrl", "java.lang.String", "String");
    	   return this;
      }

      public TiankuaiCriteria andDkUrlNotLike(java.lang.String value) {
          addCriterion("dk_url", value, ConditionMode.NOT_FUZZY, "dkUrl", "java.lang.String", "String");
          return this;
      }
      public TiankuaiCriteria andDkUrlEqualTo(java.lang.String value) {
          addCriterion("dk_url", value, ConditionMode.EQUAL, "dkUrl", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkUrlNotEqualTo(java.lang.String value) {
          addCriterion("dk_url", value, ConditionMode.NOT_EQUAL, "dkUrl", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkUrlGreaterThan(java.lang.String value) {
          addCriterion("dk_url", value, ConditionMode.GREATER_THEN, "dkUrl", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkUrlGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("dk_url", value, ConditionMode.GREATER_EQUAL, "dkUrl", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkUrlLessThan(java.lang.String value) {
          addCriterion("dk_url", value, ConditionMode.LESS_THEN, "dkUrl", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkUrlLessThanOrEqualTo(java.lang.String value) {
          addCriterion("dk_url", value, ConditionMode.LESS_EQUAL, "dkUrl", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkUrlBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("dk_url", value1, value2, ConditionMode.BETWEEN, "dkUrl", "java.lang.String", "String");
    	  return this;
      }

      public TiankuaiCriteria andDkUrlNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("dk_url", value1, value2, ConditionMode.NOT_BETWEEN, "dkUrl", "java.lang.String", "String");
          return this;
      }
        
      public TiankuaiCriteria andDkUrlIn(List<java.lang.String> values) {
          addCriterion("dk_url", values, ConditionMode.IN, "dkUrl", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkUrlNotIn(List<java.lang.String> values) {
          addCriterion("dk_url", values, ConditionMode.NOT_IN, "dkUrl", "java.lang.String", "String");
          return this;
      }
	public TiankuaiCriteria andDkBeginTimeIsNull() {
		isnull("dk_begin_time");
		return this;
	}
	
	public TiankuaiCriteria andDkBeginTimeIsNotNull() {
		notNull("dk_begin_time");
		return this;
	}
	
	public TiankuaiCriteria andDkBeginTimeIsEmpty() {
		empty("dk_begin_time");
		return this;
	}

	public TiankuaiCriteria andDkBeginTimeIsNotEmpty() {
		notEmpty("dk_begin_time");
		return this;
	}
       public TiankuaiCriteria andDkBeginTimeEqualTo(java.util.Date value) {
          addCriterion("dk_begin_time", value, ConditionMode.EQUAL, "dkBeginTime", "java.util.Date", "String");
          return this;
      }

      public TiankuaiCriteria andDkBeginTimeNotEqualTo(java.util.Date value) {
          addCriterion("dk_begin_time", value, ConditionMode.NOT_EQUAL, "dkBeginTime", "java.util.Date", "String");
          return this;
      }

      public TiankuaiCriteria andDkBeginTimeGreaterThan(java.util.Date value) {
          addCriterion("dk_begin_time", value, ConditionMode.GREATER_THEN, "dkBeginTime", "java.util.Date", "String");
          return this;
      }

      public TiankuaiCriteria andDkBeginTimeGreaterThanOrEqualTo(java.util.Date value) {
          addCriterion("dk_begin_time", value, ConditionMode.GREATER_EQUAL, "dkBeginTime", "java.util.Date", "String");
          return this;
      }

      public TiankuaiCriteria andDkBeginTimeLessThan(java.util.Date value) {
          addCriterion("dk_begin_time", value, ConditionMode.LESS_THEN, "dkBeginTime", "java.util.Date", "String");
          return this;
      }

      public TiankuaiCriteria andDkBeginTimeLessThanOrEqualTo(java.util.Date value) {
          addCriterion("dk_begin_time", value, ConditionMode.LESS_EQUAL, "dkBeginTime", "java.util.Date", "String");
          return this;
      }

      public TiankuaiCriteria andDkBeginTimeBetween(java.util.Date value1, java.util.Date value2) {
    	  addCriterion("dk_begin_time", value1, value2, ConditionMode.BETWEEN, "dkBeginTime", "java.util.Date", "String");
    	  return this;
      }

      public TiankuaiCriteria andDkBeginTimeNotBetween(java.util.Date value1, java.util.Date value2) {
          addCriterion("dk_begin_time", value1, value2, ConditionMode.NOT_BETWEEN, "dkBeginTime", "java.util.Date", "String");
          return this;
      }
        
      public TiankuaiCriteria andDkBeginTimeIn(List<java.util.Date> values) {
          addCriterion("dk_begin_time", values, ConditionMode.IN, "dkBeginTime", "java.util.Date", "String");
          return this;
      }

      public TiankuaiCriteria andDkBeginTimeNotIn(List<java.util.Date> values) {
          addCriterion("dk_begin_time", values, ConditionMode.NOT_IN, "dkBeginTime", "java.util.Date", "String");
          return this;
      }
	public TiankuaiCriteria andDkEndTimeIsNull() {
		isnull("dk_end_time");
		return this;
	}
	
	public TiankuaiCriteria andDkEndTimeIsNotNull() {
		notNull("dk_end_time");
		return this;
	}
	
	public TiankuaiCriteria andDkEndTimeIsEmpty() {
		empty("dk_end_time");
		return this;
	}

	public TiankuaiCriteria andDkEndTimeIsNotEmpty() {
		notEmpty("dk_end_time");
		return this;
	}
       public TiankuaiCriteria andDkEndTimeEqualTo(java.util.Date value) {
          addCriterion("dk_end_time", value, ConditionMode.EQUAL, "dkEndTime", "java.util.Date", "String");
          return this;
      }

      public TiankuaiCriteria andDkEndTimeNotEqualTo(java.util.Date value) {
          addCriterion("dk_end_time", value, ConditionMode.NOT_EQUAL, "dkEndTime", "java.util.Date", "String");
          return this;
      }

      public TiankuaiCriteria andDkEndTimeGreaterThan(java.util.Date value) {
          addCriterion("dk_end_time", value, ConditionMode.GREATER_THEN, "dkEndTime", "java.util.Date", "String");
          return this;
      }

      public TiankuaiCriteria andDkEndTimeGreaterThanOrEqualTo(java.util.Date value) {
          addCriterion("dk_end_time", value, ConditionMode.GREATER_EQUAL, "dkEndTime", "java.util.Date", "String");
          return this;
      }

      public TiankuaiCriteria andDkEndTimeLessThan(java.util.Date value) {
          addCriterion("dk_end_time", value, ConditionMode.LESS_THEN, "dkEndTime", "java.util.Date", "String");
          return this;
      }

      public TiankuaiCriteria andDkEndTimeLessThanOrEqualTo(java.util.Date value) {
          addCriterion("dk_end_time", value, ConditionMode.LESS_EQUAL, "dkEndTime", "java.util.Date", "String");
          return this;
      }

      public TiankuaiCriteria andDkEndTimeBetween(java.util.Date value1, java.util.Date value2) {
    	  addCriterion("dk_end_time", value1, value2, ConditionMode.BETWEEN, "dkEndTime", "java.util.Date", "String");
    	  return this;
      }

      public TiankuaiCriteria andDkEndTimeNotBetween(java.util.Date value1, java.util.Date value2) {
          addCriterion("dk_end_time", value1, value2, ConditionMode.NOT_BETWEEN, "dkEndTime", "java.util.Date", "String");
          return this;
      }
        
      public TiankuaiCriteria andDkEndTimeIn(List<java.util.Date> values) {
          addCriterion("dk_end_time", values, ConditionMode.IN, "dkEndTime", "java.util.Date", "String");
          return this;
      }

      public TiankuaiCriteria andDkEndTimeNotIn(List<java.util.Date> values) {
          addCriterion("dk_end_time", values, ConditionMode.NOT_IN, "dkEndTime", "java.util.Date", "String");
          return this;
      }
	public TiankuaiCriteria andDkFarmlandIsNull() {
		isnull("dk_farmland");
		return this;
	}
	
	public TiankuaiCriteria andDkFarmlandIsNotNull() {
		notNull("dk_farmland");
		return this;
	}
	
	public TiankuaiCriteria andDkFarmlandIsEmpty() {
		empty("dk_farmland");
		return this;
	}

	public TiankuaiCriteria andDkFarmlandIsNotEmpty() {
		notEmpty("dk_farmland");
		return this;
	}
        public TiankuaiCriteria andDkFarmlandLike(java.lang.String value) {
    	   addCriterion("dk_farmland", value, ConditionMode.FUZZY, "dkFarmland", "java.lang.String", "String");
    	   return this;
      }

      public TiankuaiCriteria andDkFarmlandNotLike(java.lang.String value) {
          addCriterion("dk_farmland", value, ConditionMode.NOT_FUZZY, "dkFarmland", "java.lang.String", "String");
          return this;
      }
      public TiankuaiCriteria andDkFarmlandEqualTo(java.lang.String value) {
          addCriterion("dk_farmland", value, ConditionMode.EQUAL, "dkFarmland", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkFarmlandNotEqualTo(java.lang.String value) {
          addCriterion("dk_farmland", value, ConditionMode.NOT_EQUAL, "dkFarmland", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkFarmlandGreaterThan(java.lang.String value) {
          addCriterion("dk_farmland", value, ConditionMode.GREATER_THEN, "dkFarmland", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkFarmlandGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("dk_farmland", value, ConditionMode.GREATER_EQUAL, "dkFarmland", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkFarmlandLessThan(java.lang.String value) {
          addCriterion("dk_farmland", value, ConditionMode.LESS_THEN, "dkFarmland", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkFarmlandLessThanOrEqualTo(java.lang.String value) {
          addCriterion("dk_farmland", value, ConditionMode.LESS_EQUAL, "dkFarmland", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkFarmlandBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("dk_farmland", value1, value2, ConditionMode.BETWEEN, "dkFarmland", "java.lang.String", "String");
    	  return this;
      }

      public TiankuaiCriteria andDkFarmlandNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("dk_farmland", value1, value2, ConditionMode.NOT_BETWEEN, "dkFarmland", "java.lang.String", "String");
          return this;
      }
        
      public TiankuaiCriteria andDkFarmlandIn(List<java.lang.String> values) {
          addCriterion("dk_farmland", values, ConditionMode.IN, "dkFarmland", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkFarmlandNotIn(List<java.lang.String> values) {
          addCriterion("dk_farmland", values, ConditionMode.NOT_IN, "dkFarmland", "java.lang.String", "String");
          return this;
      }
	public TiankuaiCriteria andDkPerimeterIsNull() {
		isnull("dk_perimeter");
		return this;
	}
	
	public TiankuaiCriteria andDkPerimeterIsNotNull() {
		notNull("dk_perimeter");
		return this;
	}
	
	public TiankuaiCriteria andDkPerimeterIsEmpty() {
		empty("dk_perimeter");
		return this;
	}

	public TiankuaiCriteria andDkPerimeterIsNotEmpty() {
		notEmpty("dk_perimeter");
		return this;
	}
        public TiankuaiCriteria andDkPerimeterLike(java.lang.String value) {
    	   addCriterion("dk_perimeter", value, ConditionMode.FUZZY, "dkPerimeter", "java.lang.String", "String");
    	   return this;
      }

      public TiankuaiCriteria andDkPerimeterNotLike(java.lang.String value) {
          addCriterion("dk_perimeter", value, ConditionMode.NOT_FUZZY, "dkPerimeter", "java.lang.String", "String");
          return this;
      }
      public TiankuaiCriteria andDkPerimeterEqualTo(java.lang.String value) {
          addCriterion("dk_perimeter", value, ConditionMode.EQUAL, "dkPerimeter", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkPerimeterNotEqualTo(java.lang.String value) {
          addCriterion("dk_perimeter", value, ConditionMode.NOT_EQUAL, "dkPerimeter", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkPerimeterGreaterThan(java.lang.String value) {
          addCriterion("dk_perimeter", value, ConditionMode.GREATER_THEN, "dkPerimeter", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkPerimeterGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("dk_perimeter", value, ConditionMode.GREATER_EQUAL, "dkPerimeter", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkPerimeterLessThan(java.lang.String value) {
          addCriterion("dk_perimeter", value, ConditionMode.LESS_THEN, "dkPerimeter", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkPerimeterLessThanOrEqualTo(java.lang.String value) {
          addCriterion("dk_perimeter", value, ConditionMode.LESS_EQUAL, "dkPerimeter", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkPerimeterBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("dk_perimeter", value1, value2, ConditionMode.BETWEEN, "dkPerimeter", "java.lang.String", "String");
    	  return this;
      }

      public TiankuaiCriteria andDkPerimeterNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("dk_perimeter", value1, value2, ConditionMode.NOT_BETWEEN, "dkPerimeter", "java.lang.String", "String");
          return this;
      }
        
      public TiankuaiCriteria andDkPerimeterIn(List<java.lang.String> values) {
          addCriterion("dk_perimeter", values, ConditionMode.IN, "dkPerimeter", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkPerimeterNotIn(List<java.lang.String> values) {
          addCriterion("dk_perimeter", values, ConditionMode.NOT_IN, "dkPerimeter", "java.lang.String", "String");
          return this;
      }
	public TiankuaiCriteria andDkFarmIsNull() {
		isnull("dk_farm");
		return this;
	}
	
	public TiankuaiCriteria andDkFarmIsNotNull() {
		notNull("dk_farm");
		return this;
	}
	
	public TiankuaiCriteria andDkFarmIsEmpty() {
		empty("dk_farm");
		return this;
	}

	public TiankuaiCriteria andDkFarmIsNotEmpty() {
		notEmpty("dk_farm");
		return this;
	}
        public TiankuaiCriteria andDkFarmLike(java.lang.String value) {
    	   addCriterion("dk_farm", value, ConditionMode.FUZZY, "dkFarm", "java.lang.String", "String");
    	   return this;
      }

      public TiankuaiCriteria andDkFarmNotLike(java.lang.String value) {
          addCriterion("dk_farm", value, ConditionMode.NOT_FUZZY, "dkFarm", "java.lang.String", "String");
          return this;
      }
      public TiankuaiCriteria andDkFarmEqualTo(java.lang.String value) {
          addCriterion("dk_farm", value, ConditionMode.EQUAL, "dkFarm", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkFarmNotEqualTo(java.lang.String value) {
          addCriterion("dk_farm", value, ConditionMode.NOT_EQUAL, "dkFarm", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkFarmGreaterThan(java.lang.String value) {
          addCriterion("dk_farm", value, ConditionMode.GREATER_THEN, "dkFarm", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkFarmGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("dk_farm", value, ConditionMode.GREATER_EQUAL, "dkFarm", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkFarmLessThan(java.lang.String value) {
          addCriterion("dk_farm", value, ConditionMode.LESS_THEN, "dkFarm", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkFarmLessThanOrEqualTo(java.lang.String value) {
          addCriterion("dk_farm", value, ConditionMode.LESS_EQUAL, "dkFarm", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkFarmBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("dk_farm", value1, value2, ConditionMode.BETWEEN, "dkFarm", "java.lang.String", "String");
    	  return this;
      }

      public TiankuaiCriteria andDkFarmNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("dk_farm", value1, value2, ConditionMode.NOT_BETWEEN, "dkFarm", "java.lang.String", "String");
          return this;
      }
        
      public TiankuaiCriteria andDkFarmIn(List<java.lang.String> values) {
          addCriterion("dk_farm", values, ConditionMode.IN, "dkFarm", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkFarmNotIn(List<java.lang.String> values) {
          addCriterion("dk_farm", values, ConditionMode.NOT_IN, "dkFarm", "java.lang.String", "String");
          return this;
      }
	public TiankuaiCriteria andDkAltitudeIsNull() {
		isnull("dk_altitude");
		return this;
	}
	
	public TiankuaiCriteria andDkAltitudeIsNotNull() {
		notNull("dk_altitude");
		return this;
	}
	
	public TiankuaiCriteria andDkAltitudeIsEmpty() {
		empty("dk_altitude");
		return this;
	}

	public TiankuaiCriteria andDkAltitudeIsNotEmpty() {
		notEmpty("dk_altitude");
		return this;
	}
        public TiankuaiCriteria andDkAltitudeLike(java.lang.String value) {
    	   addCriterion("dk_altitude", value, ConditionMode.FUZZY, "dkAltitude", "java.lang.String", "String");
    	   return this;
      }

      public TiankuaiCriteria andDkAltitudeNotLike(java.lang.String value) {
          addCriterion("dk_altitude", value, ConditionMode.NOT_FUZZY, "dkAltitude", "java.lang.String", "String");
          return this;
      }
      public TiankuaiCriteria andDkAltitudeEqualTo(java.lang.String value) {
          addCriterion("dk_altitude", value, ConditionMode.EQUAL, "dkAltitude", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkAltitudeNotEqualTo(java.lang.String value) {
          addCriterion("dk_altitude", value, ConditionMode.NOT_EQUAL, "dkAltitude", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkAltitudeGreaterThan(java.lang.String value) {
          addCriterion("dk_altitude", value, ConditionMode.GREATER_THEN, "dkAltitude", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkAltitudeGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("dk_altitude", value, ConditionMode.GREATER_EQUAL, "dkAltitude", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkAltitudeLessThan(java.lang.String value) {
          addCriterion("dk_altitude", value, ConditionMode.LESS_THEN, "dkAltitude", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkAltitudeLessThanOrEqualTo(java.lang.String value) {
          addCriterion("dk_altitude", value, ConditionMode.LESS_EQUAL, "dkAltitude", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkAltitudeBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("dk_altitude", value1, value2, ConditionMode.BETWEEN, "dkAltitude", "java.lang.String", "String");
    	  return this;
      }

      public TiankuaiCriteria andDkAltitudeNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("dk_altitude", value1, value2, ConditionMode.NOT_BETWEEN, "dkAltitude", "java.lang.String", "String");
          return this;
      }
        
      public TiankuaiCriteria andDkAltitudeIn(List<java.lang.String> values) {
          addCriterion("dk_altitude", values, ConditionMode.IN, "dkAltitude", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkAltitudeNotIn(List<java.lang.String> values) {
          addCriterion("dk_altitude", values, ConditionMode.NOT_IN, "dkAltitude", "java.lang.String", "String");
          return this;
      }
	public TiankuaiCriteria andDkSlopeIsNull() {
		isnull("dk_slope");
		return this;
	}
	
	public TiankuaiCriteria andDkSlopeIsNotNull() {
		notNull("dk_slope");
		return this;
	}
	
	public TiankuaiCriteria andDkSlopeIsEmpty() {
		empty("dk_slope");
		return this;
	}

	public TiankuaiCriteria andDkSlopeIsNotEmpty() {
		notEmpty("dk_slope");
		return this;
	}
        public TiankuaiCriteria andDkSlopeLike(java.lang.String value) {
    	   addCriterion("dk_slope", value, ConditionMode.FUZZY, "dkSlope", "java.lang.String", "String");
    	   return this;
      }

      public TiankuaiCriteria andDkSlopeNotLike(java.lang.String value) {
          addCriterion("dk_slope", value, ConditionMode.NOT_FUZZY, "dkSlope", "java.lang.String", "String");
          return this;
      }
      public TiankuaiCriteria andDkSlopeEqualTo(java.lang.String value) {
          addCriterion("dk_slope", value, ConditionMode.EQUAL, "dkSlope", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkSlopeNotEqualTo(java.lang.String value) {
          addCriterion("dk_slope", value, ConditionMode.NOT_EQUAL, "dkSlope", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkSlopeGreaterThan(java.lang.String value) {
          addCriterion("dk_slope", value, ConditionMode.GREATER_THEN, "dkSlope", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkSlopeGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("dk_slope", value, ConditionMode.GREATER_EQUAL, "dkSlope", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkSlopeLessThan(java.lang.String value) {
          addCriterion("dk_slope", value, ConditionMode.LESS_THEN, "dkSlope", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkSlopeLessThanOrEqualTo(java.lang.String value) {
          addCriterion("dk_slope", value, ConditionMode.LESS_EQUAL, "dkSlope", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkSlopeBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("dk_slope", value1, value2, ConditionMode.BETWEEN, "dkSlope", "java.lang.String", "String");
    	  return this;
      }

      public TiankuaiCriteria andDkSlopeNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("dk_slope", value1, value2, ConditionMode.NOT_BETWEEN, "dkSlope", "java.lang.String", "String");
          return this;
      }
        
      public TiankuaiCriteria andDkSlopeIn(List<java.lang.String> values) {
          addCriterion("dk_slope", values, ConditionMode.IN, "dkSlope", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkSlopeNotIn(List<java.lang.String> values) {
          addCriterion("dk_slope", values, ConditionMode.NOT_IN, "dkSlope", "java.lang.String", "String");
          return this;
      }
	public TiankuaiCriteria andDkGrowersIsNull() {
		isnull("dk_growers");
		return this;
	}
	
	public TiankuaiCriteria andDkGrowersIsNotNull() {
		notNull("dk_growers");
		return this;
	}
	
	public TiankuaiCriteria andDkGrowersIsEmpty() {
		empty("dk_growers");
		return this;
	}

	public TiankuaiCriteria andDkGrowersIsNotEmpty() {
		notEmpty("dk_growers");
		return this;
	}
        public TiankuaiCriteria andDkGrowersLike(java.lang.String value) {
    	   addCriterion("dk_growers", value, ConditionMode.FUZZY, "dkGrowers", "java.lang.String", "String");
    	   return this;
      }

      public TiankuaiCriteria andDkGrowersNotLike(java.lang.String value) {
          addCriterion("dk_growers", value, ConditionMode.NOT_FUZZY, "dkGrowers", "java.lang.String", "String");
          return this;
      }
      public TiankuaiCriteria andDkGrowersEqualTo(java.lang.String value) {
          addCriterion("dk_growers", value, ConditionMode.EQUAL, "dkGrowers", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkGrowersNotEqualTo(java.lang.String value) {
          addCriterion("dk_growers", value, ConditionMode.NOT_EQUAL, "dkGrowers", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkGrowersGreaterThan(java.lang.String value) {
          addCriterion("dk_growers", value, ConditionMode.GREATER_THEN, "dkGrowers", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkGrowersGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("dk_growers", value, ConditionMode.GREATER_EQUAL, "dkGrowers", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkGrowersLessThan(java.lang.String value) {
          addCriterion("dk_growers", value, ConditionMode.LESS_THEN, "dkGrowers", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkGrowersLessThanOrEqualTo(java.lang.String value) {
          addCriterion("dk_growers", value, ConditionMode.LESS_EQUAL, "dkGrowers", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkGrowersBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("dk_growers", value1, value2, ConditionMode.BETWEEN, "dkGrowers", "java.lang.String", "String");
    	  return this;
      }

      public TiankuaiCriteria andDkGrowersNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("dk_growers", value1, value2, ConditionMode.NOT_BETWEEN, "dkGrowers", "java.lang.String", "String");
          return this;
      }
        
      public TiankuaiCriteria andDkGrowersIn(List<java.lang.String> values) {
          addCriterion("dk_growers", values, ConditionMode.IN, "dkGrowers", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkGrowersNotIn(List<java.lang.String> values) {
          addCriterion("dk_growers", values, ConditionMode.NOT_IN, "dkGrowers", "java.lang.String", "String");
          return this;
      }
	public TiankuaiCriteria andDkPhoneIsNull() {
		isnull("dk_phone");
		return this;
	}
	
	public TiankuaiCriteria andDkPhoneIsNotNull() {
		notNull("dk_phone");
		return this;
	}
	
	public TiankuaiCriteria andDkPhoneIsEmpty() {
		empty("dk_phone");
		return this;
	}

	public TiankuaiCriteria andDkPhoneIsNotEmpty() {
		notEmpty("dk_phone");
		return this;
	}
        public TiankuaiCriteria andDkPhoneLike(java.lang.String value) {
    	   addCriterion("dk_phone", value, ConditionMode.FUZZY, "dkPhone", "java.lang.String", "String");
    	   return this;
      }

      public TiankuaiCriteria andDkPhoneNotLike(java.lang.String value) {
          addCriterion("dk_phone", value, ConditionMode.NOT_FUZZY, "dkPhone", "java.lang.String", "String");
          return this;
      }
      public TiankuaiCriteria andDkPhoneEqualTo(java.lang.String value) {
          addCriterion("dk_phone", value, ConditionMode.EQUAL, "dkPhone", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkPhoneNotEqualTo(java.lang.String value) {
          addCriterion("dk_phone", value, ConditionMode.NOT_EQUAL, "dkPhone", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkPhoneGreaterThan(java.lang.String value) {
          addCriterion("dk_phone", value, ConditionMode.GREATER_THEN, "dkPhone", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkPhoneGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("dk_phone", value, ConditionMode.GREATER_EQUAL, "dkPhone", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkPhoneLessThan(java.lang.String value) {
          addCriterion("dk_phone", value, ConditionMode.LESS_THEN, "dkPhone", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkPhoneLessThanOrEqualTo(java.lang.String value) {
          addCriterion("dk_phone", value, ConditionMode.LESS_EQUAL, "dkPhone", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkPhoneBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("dk_phone", value1, value2, ConditionMode.BETWEEN, "dkPhone", "java.lang.String", "String");
    	  return this;
      }

      public TiankuaiCriteria andDkPhoneNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("dk_phone", value1, value2, ConditionMode.NOT_BETWEEN, "dkPhone", "java.lang.String", "String");
          return this;
      }
        
      public TiankuaiCriteria andDkPhoneIn(List<java.lang.String> values) {
          addCriterion("dk_phone", values, ConditionMode.IN, "dkPhone", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkPhoneNotIn(List<java.lang.String> values) {
          addCriterion("dk_phone", values, ConditionMode.NOT_IN, "dkPhone", "java.lang.String", "String");
          return this;
      }
	public TiankuaiCriteria andDkPersonIsNull() {
		isnull("dk_person");
		return this;
	}
	
	public TiankuaiCriteria andDkPersonIsNotNull() {
		notNull("dk_person");
		return this;
	}
	
	public TiankuaiCriteria andDkPersonIsEmpty() {
		empty("dk_person");
		return this;
	}

	public TiankuaiCriteria andDkPersonIsNotEmpty() {
		notEmpty("dk_person");
		return this;
	}
        public TiankuaiCriteria andDkPersonLike(java.lang.String value) {
    	   addCriterion("dk_person", value, ConditionMode.FUZZY, "dkPerson", "java.lang.String", "String");
    	   return this;
      }

      public TiankuaiCriteria andDkPersonNotLike(java.lang.String value) {
          addCriterion("dk_person", value, ConditionMode.NOT_FUZZY, "dkPerson", "java.lang.String", "String");
          return this;
      }
      public TiankuaiCriteria andDkPersonEqualTo(java.lang.String value) {
          addCriterion("dk_person", value, ConditionMode.EQUAL, "dkPerson", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkPersonNotEqualTo(java.lang.String value) {
          addCriterion("dk_person", value, ConditionMode.NOT_EQUAL, "dkPerson", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkPersonGreaterThan(java.lang.String value) {
          addCriterion("dk_person", value, ConditionMode.GREATER_THEN, "dkPerson", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkPersonGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("dk_person", value, ConditionMode.GREATER_EQUAL, "dkPerson", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkPersonLessThan(java.lang.String value) {
          addCriterion("dk_person", value, ConditionMode.LESS_THEN, "dkPerson", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkPersonLessThanOrEqualTo(java.lang.String value) {
          addCriterion("dk_person", value, ConditionMode.LESS_EQUAL, "dkPerson", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkPersonBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("dk_person", value1, value2, ConditionMode.BETWEEN, "dkPerson", "java.lang.String", "String");
    	  return this;
      }

      public TiankuaiCriteria andDkPersonNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("dk_person", value1, value2, ConditionMode.NOT_BETWEEN, "dkPerson", "java.lang.String", "String");
          return this;
      }
        
      public TiankuaiCriteria andDkPersonIn(List<java.lang.String> values) {
          addCriterion("dk_person", values, ConditionMode.IN, "dkPerson", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkPersonNotIn(List<java.lang.String> values) {
          addCriterion("dk_person", values, ConditionMode.NOT_IN, "dkPerson", "java.lang.String", "String");
          return this;
      }
	public TiankuaiCriteria andDkFertilizerIsNull() {
		isnull("dk_fertilizer");
		return this;
	}
	
	public TiankuaiCriteria andDkFertilizerIsNotNull() {
		notNull("dk_fertilizer");
		return this;
	}
	
	public TiankuaiCriteria andDkFertilizerIsEmpty() {
		empty("dk_fertilizer");
		return this;
	}

	public TiankuaiCriteria andDkFertilizerIsNotEmpty() {
		notEmpty("dk_fertilizer");
		return this;
	}
        public TiankuaiCriteria andDkFertilizerLike(java.lang.String value) {
    	   addCriterion("dk_fertilizer", value, ConditionMode.FUZZY, "dkFertilizer", "java.lang.String", "String");
    	   return this;
      }

      public TiankuaiCriteria andDkFertilizerNotLike(java.lang.String value) {
          addCriterion("dk_fertilizer", value, ConditionMode.NOT_FUZZY, "dkFertilizer", "java.lang.String", "String");
          return this;
      }
      public TiankuaiCriteria andDkFertilizerEqualTo(java.lang.String value) {
          addCriterion("dk_fertilizer", value, ConditionMode.EQUAL, "dkFertilizer", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkFertilizerNotEqualTo(java.lang.String value) {
          addCriterion("dk_fertilizer", value, ConditionMode.NOT_EQUAL, "dkFertilizer", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkFertilizerGreaterThan(java.lang.String value) {
          addCriterion("dk_fertilizer", value, ConditionMode.GREATER_THEN, "dkFertilizer", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkFertilizerGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("dk_fertilizer", value, ConditionMode.GREATER_EQUAL, "dkFertilizer", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkFertilizerLessThan(java.lang.String value) {
          addCriterion("dk_fertilizer", value, ConditionMode.LESS_THEN, "dkFertilizer", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkFertilizerLessThanOrEqualTo(java.lang.String value) {
          addCriterion("dk_fertilizer", value, ConditionMode.LESS_EQUAL, "dkFertilizer", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkFertilizerBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("dk_fertilizer", value1, value2, ConditionMode.BETWEEN, "dkFertilizer", "java.lang.String", "String");
    	  return this;
      }

      public TiankuaiCriteria andDkFertilizerNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("dk_fertilizer", value1, value2, ConditionMode.NOT_BETWEEN, "dkFertilizer", "java.lang.String", "String");
          return this;
      }
        
      public TiankuaiCriteria andDkFertilizerIn(List<java.lang.String> values) {
          addCriterion("dk_fertilizer", values, ConditionMode.IN, "dkFertilizer", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkFertilizerNotIn(List<java.lang.String> values) {
          addCriterion("dk_fertilizer", values, ConditionMode.NOT_IN, "dkFertilizer", "java.lang.String", "String");
          return this;
      }
	public TiankuaiCriteria andDkUsernameIsNull() {
		isnull("dk_username");
		return this;
	}
	
	public TiankuaiCriteria andDkUsernameIsNotNull() {
		notNull("dk_username");
		return this;
	}
	
	public TiankuaiCriteria andDkUsernameIsEmpty() {
		empty("dk_username");
		return this;
	}

	public TiankuaiCriteria andDkUsernameIsNotEmpty() {
		notEmpty("dk_username");
		return this;
	}
        public TiankuaiCriteria andDkUsernameLike(java.lang.String value) {
    	   addCriterion("dk_username", value, ConditionMode.FUZZY, "dkUsername", "java.lang.String", "String");
    	   return this;
      }

      public TiankuaiCriteria andDkUsernameNotLike(java.lang.String value) {
          addCriterion("dk_username", value, ConditionMode.NOT_FUZZY, "dkUsername", "java.lang.String", "String");
          return this;
      }
      public TiankuaiCriteria andDkUsernameEqualTo(java.lang.String value) {
          addCriterion("dk_username", value, ConditionMode.EQUAL, "dkUsername", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkUsernameNotEqualTo(java.lang.String value) {
          addCriterion("dk_username", value, ConditionMode.NOT_EQUAL, "dkUsername", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkUsernameGreaterThan(java.lang.String value) {
          addCriterion("dk_username", value, ConditionMode.GREATER_THEN, "dkUsername", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkUsernameGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("dk_username", value, ConditionMode.GREATER_EQUAL, "dkUsername", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkUsernameLessThan(java.lang.String value) {
          addCriterion("dk_username", value, ConditionMode.LESS_THEN, "dkUsername", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkUsernameLessThanOrEqualTo(java.lang.String value) {
          addCriterion("dk_username", value, ConditionMode.LESS_EQUAL, "dkUsername", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkUsernameBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("dk_username", value1, value2, ConditionMode.BETWEEN, "dkUsername", "java.lang.String", "String");
    	  return this;
      }

      public TiankuaiCriteria andDkUsernameNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("dk_username", value1, value2, ConditionMode.NOT_BETWEEN, "dkUsername", "java.lang.String", "String");
          return this;
      }
        
      public TiankuaiCriteria andDkUsernameIn(List<java.lang.String> values) {
          addCriterion("dk_username", values, ConditionMode.IN, "dkUsername", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkUsernameNotIn(List<java.lang.String> values) {
          addCriterion("dk_username", values, ConditionMode.NOT_IN, "dkUsername", "java.lang.String", "String");
          return this;
      }
	public TiankuaiCriteria andDkUserIdIsNull() {
		isnull("dk_user_id");
		return this;
	}
	
	public TiankuaiCriteria andDkUserIdIsNotNull() {
		notNull("dk_user_id");
		return this;
	}
	
	public TiankuaiCriteria andDkUserIdIsEmpty() {
		empty("dk_user_id");
		return this;
	}

	public TiankuaiCriteria andDkUserIdIsNotEmpty() {
		notEmpty("dk_user_id");
		return this;
	}
       public TiankuaiCriteria andDkUserIdEqualTo(java.lang.Integer value) {
          addCriterion("dk_user_id", value, ConditionMode.EQUAL, "dkUserId", "java.lang.Integer", "Float");
          return this;
      }

      public TiankuaiCriteria andDkUserIdNotEqualTo(java.lang.Integer value) {
          addCriterion("dk_user_id", value, ConditionMode.NOT_EQUAL, "dkUserId", "java.lang.Integer", "Float");
          return this;
      }

      public TiankuaiCriteria andDkUserIdGreaterThan(java.lang.Integer value) {
          addCriterion("dk_user_id", value, ConditionMode.GREATER_THEN, "dkUserId", "java.lang.Integer", "Float");
          return this;
      }

      public TiankuaiCriteria andDkUserIdGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("dk_user_id", value, ConditionMode.GREATER_EQUAL, "dkUserId", "java.lang.Integer", "Float");
          return this;
      }

      public TiankuaiCriteria andDkUserIdLessThan(java.lang.Integer value) {
          addCriterion("dk_user_id", value, ConditionMode.LESS_THEN, "dkUserId", "java.lang.Integer", "Float");
          return this;
      }

      public TiankuaiCriteria andDkUserIdLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("dk_user_id", value, ConditionMode.LESS_EQUAL, "dkUserId", "java.lang.Integer", "Float");
          return this;
      }

      public TiankuaiCriteria andDkUserIdBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("dk_user_id", value1, value2, ConditionMode.BETWEEN, "dkUserId", "java.lang.Integer", "Float");
    	  return this;
      }

      public TiankuaiCriteria andDkUserIdNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("dk_user_id", value1, value2, ConditionMode.NOT_BETWEEN, "dkUserId", "java.lang.Integer", "Float");
          return this;
      }
        
      public TiankuaiCriteria andDkUserIdIn(List<java.lang.Integer> values) {
          addCriterion("dk_user_id", values, ConditionMode.IN, "dkUserId", "java.lang.Integer", "Float");
          return this;
      }

      public TiankuaiCriteria andDkUserIdNotIn(List<java.lang.Integer> values) {
          addCriterion("dk_user_id", values, ConditionMode.NOT_IN, "dkUserId", "java.lang.Integer", "Float");
          return this;
      }
	public TiankuaiCriteria andDelIsNull() {
		isnull("del");
		return this;
	}
	
	public TiankuaiCriteria andDelIsNotNull() {
		notNull("del");
		return this;
	}
	
	public TiankuaiCriteria andDelIsEmpty() {
		empty("del");
		return this;
	}

	public TiankuaiCriteria andDelIsNotEmpty() {
		notEmpty("del");
		return this;
	}
       public TiankuaiCriteria andDelEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public TiankuaiCriteria andDelNotEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.NOT_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public TiankuaiCriteria andDelGreaterThan(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.GREATER_THEN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public TiankuaiCriteria andDelGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.GREATER_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public TiankuaiCriteria andDelLessThan(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.LESS_THEN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public TiankuaiCriteria andDelLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.LESS_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public TiankuaiCriteria andDelBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("del", value1, value2, ConditionMode.BETWEEN, "del", "java.lang.Integer", "Float");
    	  return this;
      }

      public TiankuaiCriteria andDelNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("del", value1, value2, ConditionMode.NOT_BETWEEN, "del", "java.lang.Integer", "Float");
          return this;
      }
        
      public TiankuaiCriteria andDelIn(List<java.lang.Integer> values) {
          addCriterion("del", values, ConditionMode.IN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public TiankuaiCriteria andDelNotIn(List<java.lang.Integer> values) {
          addCriterion("del", values, ConditionMode.NOT_IN, "del", "java.lang.Integer", "Float");
          return this;
      }
	public TiankuaiCriteria andDkTimeIsNull() {
		isnull("dk_time");
		return this;
	}
	
	public TiankuaiCriteria andDkTimeIsNotNull() {
		notNull("dk_time");
		return this;
	}
	
	public TiankuaiCriteria andDkTimeIsEmpty() {
		empty("dk_time");
		return this;
	}

	public TiankuaiCriteria andDkTimeIsNotEmpty() {
		notEmpty("dk_time");
		return this;
	}
       public TiankuaiCriteria andDkTimeEqualTo(java.util.Date value) {
          addCriterion("dk_time", value, ConditionMode.EQUAL, "dkTime", "java.util.Date", "String");
          return this;
      }

      public TiankuaiCriteria andDkTimeNotEqualTo(java.util.Date value) {
          addCriterion("dk_time", value, ConditionMode.NOT_EQUAL, "dkTime", "java.util.Date", "String");
          return this;
      }

      public TiankuaiCriteria andDkTimeGreaterThan(java.util.Date value) {
          addCriterion("dk_time", value, ConditionMode.GREATER_THEN, "dkTime", "java.util.Date", "String");
          return this;
      }

      public TiankuaiCriteria andDkTimeGreaterThanOrEqualTo(java.util.Date value) {
          addCriterion("dk_time", value, ConditionMode.GREATER_EQUAL, "dkTime", "java.util.Date", "String");
          return this;
      }

      public TiankuaiCriteria andDkTimeLessThan(java.util.Date value) {
          addCriterion("dk_time", value, ConditionMode.LESS_THEN, "dkTime", "java.util.Date", "String");
          return this;
      }

      public TiankuaiCriteria andDkTimeLessThanOrEqualTo(java.util.Date value) {
          addCriterion("dk_time", value, ConditionMode.LESS_EQUAL, "dkTime", "java.util.Date", "String");
          return this;
      }

      public TiankuaiCriteria andDkTimeBetween(java.util.Date value1, java.util.Date value2) {
    	  addCriterion("dk_time", value1, value2, ConditionMode.BETWEEN, "dkTime", "java.util.Date", "String");
    	  return this;
      }

      public TiankuaiCriteria andDkTimeNotBetween(java.util.Date value1, java.util.Date value2) {
          addCriterion("dk_time", value1, value2, ConditionMode.NOT_BETWEEN, "dkTime", "java.util.Date", "String");
          return this;
      }
        
      public TiankuaiCriteria andDkTimeIn(List<java.util.Date> values) {
          addCriterion("dk_time", values, ConditionMode.IN, "dkTime", "java.util.Date", "String");
          return this;
      }

      public TiankuaiCriteria andDkTimeNotIn(List<java.util.Date> values) {
          addCriterion("dk_time", values, ConditionMode.NOT_IN, "dkTime", "java.util.Date", "String");
          return this;
      }
	public TiankuaiCriteria andDkTypeIsNull() {
		isnull("dk_type");
		return this;
	}
	
	public TiankuaiCriteria andDkTypeIsNotNull() {
		notNull("dk_type");
		return this;
	}
	
	public TiankuaiCriteria andDkTypeIsEmpty() {
		empty("dk_type");
		return this;
	}

	public TiankuaiCriteria andDkTypeIsNotEmpty() {
		notEmpty("dk_type");
		return this;
	}
        public TiankuaiCriteria andDkTypeLike(java.lang.String value) {
    	   addCriterion("dk_type", value, ConditionMode.FUZZY, "dkType", "java.lang.String", "String");
    	   return this;
      }

      public TiankuaiCriteria andDkTypeNotLike(java.lang.String value) {
          addCriterion("dk_type", value, ConditionMode.NOT_FUZZY, "dkType", "java.lang.String", "String");
          return this;
      }
      public TiankuaiCriteria andDkTypeEqualTo(java.lang.String value) {
          addCriterion("dk_type", value, ConditionMode.EQUAL, "dkType", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkTypeNotEqualTo(java.lang.String value) {
          addCriterion("dk_type", value, ConditionMode.NOT_EQUAL, "dkType", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkTypeGreaterThan(java.lang.String value) {
          addCriterion("dk_type", value, ConditionMode.GREATER_THEN, "dkType", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkTypeGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("dk_type", value, ConditionMode.GREATER_EQUAL, "dkType", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkTypeLessThan(java.lang.String value) {
          addCriterion("dk_type", value, ConditionMode.LESS_THEN, "dkType", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkTypeLessThanOrEqualTo(java.lang.String value) {
          addCriterion("dk_type", value, ConditionMode.LESS_EQUAL, "dkType", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkTypeBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("dk_type", value1, value2, ConditionMode.BETWEEN, "dkType", "java.lang.String", "String");
    	  return this;
      }

      public TiankuaiCriteria andDkTypeNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("dk_type", value1, value2, ConditionMode.NOT_BETWEEN, "dkType", "java.lang.String", "String");
          return this;
      }
        
      public TiankuaiCriteria andDkTypeIn(List<java.lang.String> values) {
          addCriterion("dk_type", values, ConditionMode.IN, "dkType", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkTypeNotIn(List<java.lang.String> values) {
          addCriterion("dk_type", values, ConditionMode.NOT_IN, "dkType", "java.lang.String", "String");
          return this;
      }
	public TiankuaiCriteria andDkDensityIsNull() {
		isnull("dk_density");
		return this;
	}
	
	public TiankuaiCriteria andDkDensityIsNotNull() {
		notNull("dk_density");
		return this;
	}
	
	public TiankuaiCriteria andDkDensityIsEmpty() {
		empty("dk_density");
		return this;
	}

	public TiankuaiCriteria andDkDensityIsNotEmpty() {
		notEmpty("dk_density");
		return this;
	}
        public TiankuaiCriteria andDkDensityLike(java.lang.String value) {
    	   addCriterion("dk_density", value, ConditionMode.FUZZY, "dkDensity", "java.lang.String", "String");
    	   return this;
      }

      public TiankuaiCriteria andDkDensityNotLike(java.lang.String value) {
          addCriterion("dk_density", value, ConditionMode.NOT_FUZZY, "dkDensity", "java.lang.String", "String");
          return this;
      }
      public TiankuaiCriteria andDkDensityEqualTo(java.lang.String value) {
          addCriterion("dk_density", value, ConditionMode.EQUAL, "dkDensity", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkDensityNotEqualTo(java.lang.String value) {
          addCriterion("dk_density", value, ConditionMode.NOT_EQUAL, "dkDensity", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkDensityGreaterThan(java.lang.String value) {
          addCriterion("dk_density", value, ConditionMode.GREATER_THEN, "dkDensity", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkDensityGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("dk_density", value, ConditionMode.GREATER_EQUAL, "dkDensity", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkDensityLessThan(java.lang.String value) {
          addCriterion("dk_density", value, ConditionMode.LESS_THEN, "dkDensity", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkDensityLessThanOrEqualTo(java.lang.String value) {
          addCriterion("dk_density", value, ConditionMode.LESS_EQUAL, "dkDensity", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkDensityBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("dk_density", value1, value2, ConditionMode.BETWEEN, "dkDensity", "java.lang.String", "String");
    	  return this;
      }

      public TiankuaiCriteria andDkDensityNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("dk_density", value1, value2, ConditionMode.NOT_BETWEEN, "dkDensity", "java.lang.String", "String");
          return this;
      }
        
      public TiankuaiCriteria andDkDensityIn(List<java.lang.String> values) {
          addCriterion("dk_density", values, ConditionMode.IN, "dkDensity", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkDensityNotIn(List<java.lang.String> values) {
          addCriterion("dk_density", values, ConditionMode.NOT_IN, "dkDensity", "java.lang.String", "String");
          return this;
      }
	public TiankuaiCriteria andDkIrrigationIsNull() {
		isnull("dk_irrigation");
		return this;
	}
	
	public TiankuaiCriteria andDkIrrigationIsNotNull() {
		notNull("dk_irrigation");
		return this;
	}
	
	public TiankuaiCriteria andDkIrrigationIsEmpty() {
		empty("dk_irrigation");
		return this;
	}

	public TiankuaiCriteria andDkIrrigationIsNotEmpty() {
		notEmpty("dk_irrigation");
		return this;
	}
        public TiankuaiCriteria andDkIrrigationLike(java.lang.String value) {
    	   addCriterion("dk_irrigation", value, ConditionMode.FUZZY, "dkIrrigation", "java.lang.String", "String");
    	   return this;
      }

      public TiankuaiCriteria andDkIrrigationNotLike(java.lang.String value) {
          addCriterion("dk_irrigation", value, ConditionMode.NOT_FUZZY, "dkIrrigation", "java.lang.String", "String");
          return this;
      }
      public TiankuaiCriteria andDkIrrigationEqualTo(java.lang.String value) {
          addCriterion("dk_irrigation", value, ConditionMode.EQUAL, "dkIrrigation", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkIrrigationNotEqualTo(java.lang.String value) {
          addCriterion("dk_irrigation", value, ConditionMode.NOT_EQUAL, "dkIrrigation", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkIrrigationGreaterThan(java.lang.String value) {
          addCriterion("dk_irrigation", value, ConditionMode.GREATER_THEN, "dkIrrigation", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkIrrigationGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("dk_irrigation", value, ConditionMode.GREATER_EQUAL, "dkIrrigation", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkIrrigationLessThan(java.lang.String value) {
          addCriterion("dk_irrigation", value, ConditionMode.LESS_THEN, "dkIrrigation", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkIrrigationLessThanOrEqualTo(java.lang.String value) {
          addCriterion("dk_irrigation", value, ConditionMode.LESS_EQUAL, "dkIrrigation", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkIrrigationBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("dk_irrigation", value1, value2, ConditionMode.BETWEEN, "dkIrrigation", "java.lang.String", "String");
    	  return this;
      }

      public TiankuaiCriteria andDkIrrigationNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("dk_irrigation", value1, value2, ConditionMode.NOT_BETWEEN, "dkIrrigation", "java.lang.String", "String");
          return this;
      }
        
      public TiankuaiCriteria andDkIrrigationIn(List<java.lang.String> values) {
          addCriterion("dk_irrigation", values, ConditionMode.IN, "dkIrrigation", "java.lang.String", "String");
          return this;
      }

      public TiankuaiCriteria andDkIrrigationNotIn(List<java.lang.String> values) {
          addCriterion("dk_irrigation", values, ConditionMode.NOT_IN, "dkIrrigation", "java.lang.String", "String");
          return this;
      }
}