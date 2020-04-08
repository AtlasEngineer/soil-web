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
public class CatalogueSampleCriteria extends Columns implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static CatalogueSampleCriteria create() {
		return new CatalogueSampleCriteria();
	}
	
	public static CatalogueSampleCriteria create(Column column) {
		CatalogueSampleCriteria that = new CatalogueSampleCriteria();
		that.add(column);
        return that;
    }

    public static CatalogueSampleCriteria create(String name, Object value) {
        return (CatalogueSampleCriteria) create().eq(name, value);
    }
    
    public Example example() {
    	return Example.create("catalogue_sample", this);
    }
    
    /**
     * equals
     *
     * @param name
     * @param value
     * @return
     */
    public CatalogueSampleCriteria eq(String name, Object value) {
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
    public CatalogueSampleCriteria ne(String name, Object value) {
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

    public CatalogueSampleCriteria like(String name, Object value) {
    	super.like(name, value);
        return this;
    }
    
    public CatalogueSampleCriteria notLike(String name, Object value) {
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
    public CatalogueSampleCriteria gt(String name, Object value) {
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
    public CatalogueSampleCriteria ge(String name, Object value) {
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
    public CatalogueSampleCriteria lt(String name, Object value) {
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
    public CatalogueSampleCriteria le(String name, Object value) {
    	super.le(name, value);
        return this;
    }
    
    public CatalogueSampleCriteria isnull(String name) {
    	super.isnull(name);
        return this;
    } 

    public CatalogueSampleCriteria notNull(String name) {
    	super.notNull(name);
        return this;
    } 
    
    public CatalogueSampleCriteria empty(String name) {
    	super.empty(name);
        return this;
    } 
    
    public CatalogueSampleCriteria notEmpty(String name) {
    	super.notEmpty(name);
        return this;
    } 
    
    public CatalogueSampleCriteria add(Column column) {
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
		 
	public CatalogueSampleCriteria andIdIsNull() {
		isnull("id");
		return this;
	}
	
	public CatalogueSampleCriteria andIdIsNotNull() {
		notNull("id");
		return this;
	}
	
	public CatalogueSampleCriteria andIdIsEmpty() {
		empty("id");
		return this;
	}

	public CatalogueSampleCriteria andIdIsNotEmpty() {
		notEmpty("id");
		return this;
	}
        public CatalogueSampleCriteria andIdLike(java.lang.String value) {
    	   addCriterion("id", value, ConditionMode.FUZZY, "id", "java.lang.String", "String");
    	   return this;
      }

      public CatalogueSampleCriteria andIdNotLike(java.lang.String value) {
          addCriterion("id", value, ConditionMode.NOT_FUZZY, "id", "java.lang.String", "String");
          return this;
      }
      public CatalogueSampleCriteria andIdEqualTo(java.lang.String value) {
          addCriterion("id", value, ConditionMode.EQUAL, "id", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andIdNotEqualTo(java.lang.String value) {
          addCriterion("id", value, ConditionMode.NOT_EQUAL, "id", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andIdGreaterThan(java.lang.String value) {
          addCriterion("id", value, ConditionMode.GREATER_THEN, "id", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andIdGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("id", value, ConditionMode.GREATER_EQUAL, "id", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andIdLessThan(java.lang.String value) {
          addCriterion("id", value, ConditionMode.LESS_THEN, "id", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andIdLessThanOrEqualTo(java.lang.String value) {
          addCriterion("id", value, ConditionMode.LESS_EQUAL, "id", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andIdBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("id", value1, value2, ConditionMode.BETWEEN, "id", "java.lang.String", "String");
    	  return this;
      }

      public CatalogueSampleCriteria andIdNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("id", value1, value2, ConditionMode.NOT_BETWEEN, "id", "java.lang.String", "String");
          return this;
      }
        
      public CatalogueSampleCriteria andIdIn(List<java.lang.String> values) {
          addCriterion("id", values, ConditionMode.IN, "id", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andIdNotIn(List<java.lang.String> values) {
          addCriterion("id", values, ConditionMode.NOT_IN, "id", "java.lang.String", "String");
          return this;
      }
	public CatalogueSampleCriteria andNameIsNull() {
		isnull("name");
		return this;
	}
	
	public CatalogueSampleCriteria andNameIsNotNull() {
		notNull("name");
		return this;
	}
	
	public CatalogueSampleCriteria andNameIsEmpty() {
		empty("name");
		return this;
	}

	public CatalogueSampleCriteria andNameIsNotEmpty() {
		notEmpty("name");
		return this;
	}
        public CatalogueSampleCriteria andNameLike(java.lang.String value) {
    	   addCriterion("name", value, ConditionMode.FUZZY, "name", "java.lang.String", "String");
    	   return this;
      }

      public CatalogueSampleCriteria andNameNotLike(java.lang.String value) {
          addCriterion("name", value, ConditionMode.NOT_FUZZY, "name", "java.lang.String", "String");
          return this;
      }
      public CatalogueSampleCriteria andNameEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andNameNotEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.NOT_EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andNameGreaterThan(java.lang.String value) {
          addCriterion("name", value, ConditionMode.GREATER_THEN, "name", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andNameGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.GREATER_EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andNameLessThan(java.lang.String value) {
          addCriterion("name", value, ConditionMode.LESS_THEN, "name", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andNameLessThanOrEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.LESS_EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andNameBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("name", value1, value2, ConditionMode.BETWEEN, "name", "java.lang.String", "String");
    	  return this;
      }

      public CatalogueSampleCriteria andNameNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("name", value1, value2, ConditionMode.NOT_BETWEEN, "name", "java.lang.String", "String");
          return this;
      }
        
      public CatalogueSampleCriteria andNameIn(List<java.lang.String> values) {
          addCriterion("name", values, ConditionMode.IN, "name", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andNameNotIn(List<java.lang.String> values) {
          addCriterion("name", values, ConditionMode.NOT_IN, "name", "java.lang.String", "String");
          return this;
      }
	public CatalogueSampleCriteria andBriefIsNull() {
		isnull("brief");
		return this;
	}
	
	public CatalogueSampleCriteria andBriefIsNotNull() {
		notNull("brief");
		return this;
	}
	
	public CatalogueSampleCriteria andBriefIsEmpty() {
		empty("brief");
		return this;
	}

	public CatalogueSampleCriteria andBriefIsNotEmpty() {
		notEmpty("brief");
		return this;
	}
        public CatalogueSampleCriteria andBriefLike(java.lang.String value) {
    	   addCriterion("brief", value, ConditionMode.FUZZY, "brief", "java.lang.String", "String");
    	   return this;
      }

      public CatalogueSampleCriteria andBriefNotLike(java.lang.String value) {
          addCriterion("brief", value, ConditionMode.NOT_FUZZY, "brief", "java.lang.String", "String");
          return this;
      }
      public CatalogueSampleCriteria andBriefEqualTo(java.lang.String value) {
          addCriterion("brief", value, ConditionMode.EQUAL, "brief", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andBriefNotEqualTo(java.lang.String value) {
          addCriterion("brief", value, ConditionMode.NOT_EQUAL, "brief", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andBriefGreaterThan(java.lang.String value) {
          addCriterion("brief", value, ConditionMode.GREATER_THEN, "brief", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andBriefGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("brief", value, ConditionMode.GREATER_EQUAL, "brief", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andBriefLessThan(java.lang.String value) {
          addCriterion("brief", value, ConditionMode.LESS_THEN, "brief", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andBriefLessThanOrEqualTo(java.lang.String value) {
          addCriterion("brief", value, ConditionMode.LESS_EQUAL, "brief", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andBriefBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("brief", value1, value2, ConditionMode.BETWEEN, "brief", "java.lang.String", "String");
    	  return this;
      }

      public CatalogueSampleCriteria andBriefNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("brief", value1, value2, ConditionMode.NOT_BETWEEN, "brief", "java.lang.String", "String");
          return this;
      }
        
      public CatalogueSampleCriteria andBriefIn(List<java.lang.String> values) {
          addCriterion("brief", values, ConditionMode.IN, "brief", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andBriefNotIn(List<java.lang.String> values) {
          addCriterion("brief", values, ConditionMode.NOT_IN, "brief", "java.lang.String", "String");
          return this;
      }
	public CatalogueSampleCriteria andCatalogueIdIsNull() {
		isnull("catalogue_id");
		return this;
	}
	
	public CatalogueSampleCriteria andCatalogueIdIsNotNull() {
		notNull("catalogue_id");
		return this;
	}
	
	public CatalogueSampleCriteria andCatalogueIdIsEmpty() {
		empty("catalogue_id");
		return this;
	}

	public CatalogueSampleCriteria andCatalogueIdIsNotEmpty() {
		notEmpty("catalogue_id");
		return this;
	}
        public CatalogueSampleCriteria andCatalogueIdLike(java.lang.String value) {
    	   addCriterion("catalogue_id", value, ConditionMode.FUZZY, "catalogueId", "java.lang.String", "String");
    	   return this;
      }

      public CatalogueSampleCriteria andCatalogueIdNotLike(java.lang.String value) {
          addCriterion("catalogue_id", value, ConditionMode.NOT_FUZZY, "catalogueId", "java.lang.String", "String");
          return this;
      }
      public CatalogueSampleCriteria andCatalogueIdEqualTo(java.lang.String value) {
          addCriterion("catalogue_id", value, ConditionMode.EQUAL, "catalogueId", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andCatalogueIdNotEqualTo(java.lang.String value) {
          addCriterion("catalogue_id", value, ConditionMode.NOT_EQUAL, "catalogueId", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andCatalogueIdGreaterThan(java.lang.String value) {
          addCriterion("catalogue_id", value, ConditionMode.GREATER_THEN, "catalogueId", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andCatalogueIdGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("catalogue_id", value, ConditionMode.GREATER_EQUAL, "catalogueId", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andCatalogueIdLessThan(java.lang.String value) {
          addCriterion("catalogue_id", value, ConditionMode.LESS_THEN, "catalogueId", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andCatalogueIdLessThanOrEqualTo(java.lang.String value) {
          addCriterion("catalogue_id", value, ConditionMode.LESS_EQUAL, "catalogueId", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andCatalogueIdBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("catalogue_id", value1, value2, ConditionMode.BETWEEN, "catalogueId", "java.lang.String", "String");
    	  return this;
      }

      public CatalogueSampleCriteria andCatalogueIdNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("catalogue_id", value1, value2, ConditionMode.NOT_BETWEEN, "catalogueId", "java.lang.String", "String");
          return this;
      }
        
      public CatalogueSampleCriteria andCatalogueIdIn(List<java.lang.String> values) {
          addCriterion("catalogue_id", values, ConditionMode.IN, "catalogueId", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andCatalogueIdNotIn(List<java.lang.String> values) {
          addCriterion("catalogue_id", values, ConditionMode.NOT_IN, "catalogueId", "java.lang.String", "String");
          return this;
      }
	public CatalogueSampleCriteria andUrlIsNull() {
		isnull("url");
		return this;
	}
	
	public CatalogueSampleCriteria andUrlIsNotNull() {
		notNull("url");
		return this;
	}
	
	public CatalogueSampleCriteria andUrlIsEmpty() {
		empty("url");
		return this;
	}

	public CatalogueSampleCriteria andUrlIsNotEmpty() {
		notEmpty("url");
		return this;
	}
        public CatalogueSampleCriteria andUrlLike(java.lang.String value) {
    	   addCriterion("url", value, ConditionMode.FUZZY, "url", "java.lang.String", "String");
    	   return this;
      }

      public CatalogueSampleCriteria andUrlNotLike(java.lang.String value) {
          addCriterion("url", value, ConditionMode.NOT_FUZZY, "url", "java.lang.String", "String");
          return this;
      }
      public CatalogueSampleCriteria andUrlEqualTo(java.lang.String value) {
          addCriterion("url", value, ConditionMode.EQUAL, "url", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andUrlNotEqualTo(java.lang.String value) {
          addCriterion("url", value, ConditionMode.NOT_EQUAL, "url", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andUrlGreaterThan(java.lang.String value) {
          addCriterion("url", value, ConditionMode.GREATER_THEN, "url", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andUrlGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("url", value, ConditionMode.GREATER_EQUAL, "url", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andUrlLessThan(java.lang.String value) {
          addCriterion("url", value, ConditionMode.LESS_THEN, "url", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andUrlLessThanOrEqualTo(java.lang.String value) {
          addCriterion("url", value, ConditionMode.LESS_EQUAL, "url", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andUrlBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("url", value1, value2, ConditionMode.BETWEEN, "url", "java.lang.String", "String");
    	  return this;
      }

      public CatalogueSampleCriteria andUrlNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("url", value1, value2, ConditionMode.NOT_BETWEEN, "url", "java.lang.String", "String");
          return this;
      }
        
      public CatalogueSampleCriteria andUrlIn(List<java.lang.String> values) {
          addCriterion("url", values, ConditionMode.IN, "url", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andUrlNotIn(List<java.lang.String> values) {
          addCriterion("url", values, ConditionMode.NOT_IN, "url", "java.lang.String", "String");
          return this;
      }
	public CatalogueSampleCriteria andDelIsNull() {
		isnull("del");
		return this;
	}
	
	public CatalogueSampleCriteria andDelIsNotNull() {
		notNull("del");
		return this;
	}
	
	public CatalogueSampleCriteria andDelIsEmpty() {
		empty("del");
		return this;
	}

	public CatalogueSampleCriteria andDelIsNotEmpty() {
		notEmpty("del");
		return this;
	}
       public CatalogueSampleCriteria andDelEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueSampleCriteria andDelNotEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.NOT_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueSampleCriteria andDelGreaterThan(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.GREATER_THEN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueSampleCriteria andDelGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.GREATER_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueSampleCriteria andDelLessThan(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.LESS_THEN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueSampleCriteria andDelLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.LESS_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueSampleCriteria andDelBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("del", value1, value2, ConditionMode.BETWEEN, "del", "java.lang.Integer", "Float");
    	  return this;
      }

      public CatalogueSampleCriteria andDelNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("del", value1, value2, ConditionMode.NOT_BETWEEN, "del", "java.lang.Integer", "Float");
          return this;
      }
        
      public CatalogueSampleCriteria andDelIn(List<java.lang.Integer> values) {
          addCriterion("del", values, ConditionMode.IN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueSampleCriteria andDelNotIn(List<java.lang.Integer> values) {
          addCriterion("del", values, ConditionMode.NOT_IN, "del", "java.lang.Integer", "Float");
          return this;
      }
	public CatalogueSampleCriteria andContSignIsNull() {
		isnull("cont_sign");
		return this;
	}
	
	public CatalogueSampleCriteria andContSignIsNotNull() {
		notNull("cont_sign");
		return this;
	}
	
	public CatalogueSampleCriteria andContSignIsEmpty() {
		empty("cont_sign");
		return this;
	}

	public CatalogueSampleCriteria andContSignIsNotEmpty() {
		notEmpty("cont_sign");
		return this;
	}
        public CatalogueSampleCriteria andContSignLike(java.lang.String value) {
    	   addCriterion("cont_sign", value, ConditionMode.FUZZY, "contSign", "java.lang.String", "Float");
    	   return this;
      }

      public CatalogueSampleCriteria andContSignNotLike(java.lang.String value) {
          addCriterion("cont_sign", value, ConditionMode.NOT_FUZZY, "contSign", "java.lang.String", "Float");
          return this;
      }
      public CatalogueSampleCriteria andContSignEqualTo(java.lang.String value) {
          addCriterion("cont_sign", value, ConditionMode.EQUAL, "contSign", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andContSignNotEqualTo(java.lang.String value) {
          addCriterion("cont_sign", value, ConditionMode.NOT_EQUAL, "contSign", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andContSignGreaterThan(java.lang.String value) {
          addCriterion("cont_sign", value, ConditionMode.GREATER_THEN, "contSign", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andContSignGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("cont_sign", value, ConditionMode.GREATER_EQUAL, "contSign", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andContSignLessThan(java.lang.String value) {
          addCriterion("cont_sign", value, ConditionMode.LESS_THEN, "contSign", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andContSignLessThanOrEqualTo(java.lang.String value) {
          addCriterion("cont_sign", value, ConditionMode.LESS_EQUAL, "contSign", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andContSignBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("cont_sign", value1, value2, ConditionMode.BETWEEN, "contSign", "java.lang.String", "String");
    	  return this;
      }

      public CatalogueSampleCriteria andContSignNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("cont_sign", value1, value2, ConditionMode.NOT_BETWEEN, "contSign", "java.lang.String", "String");
          return this;
      }
        
      public CatalogueSampleCriteria andContSignIn(List<java.lang.String> values) {
          addCriterion("cont_sign", values, ConditionMode.IN, "contSign", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andContSignNotIn(List<java.lang.String> values) {
          addCriterion("cont_sign", values, ConditionMode.NOT_IN, "contSign", "java.lang.String", "String");
          return this;
      }
	public CatalogueSampleCriteria andLonIsNull() {
		isnull("lon");
		return this;
	}
	
	public CatalogueSampleCriteria andLonIsNotNull() {
		notNull("lon");
		return this;
	}
	
	public CatalogueSampleCriteria andLonIsEmpty() {
		empty("lon");
		return this;
	}

	public CatalogueSampleCriteria andLonIsNotEmpty() {
		notEmpty("lon");
		return this;
	}
        public CatalogueSampleCriteria andLonLike(java.lang.String value) {
    	   addCriterion("lon", value, ConditionMode.FUZZY, "lon", "java.lang.String", "String");
    	   return this;
      }

      public CatalogueSampleCriteria andLonNotLike(java.lang.String value) {
          addCriterion("lon", value, ConditionMode.NOT_FUZZY, "lon", "java.lang.String", "String");
          return this;
      }
      public CatalogueSampleCriteria andLonEqualTo(java.lang.String value) {
          addCriterion("lon", value, ConditionMode.EQUAL, "lon", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andLonNotEqualTo(java.lang.String value) {
          addCriterion("lon", value, ConditionMode.NOT_EQUAL, "lon", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andLonGreaterThan(java.lang.String value) {
          addCriterion("lon", value, ConditionMode.GREATER_THEN, "lon", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andLonGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("lon", value, ConditionMode.GREATER_EQUAL, "lon", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andLonLessThan(java.lang.String value) {
          addCriterion("lon", value, ConditionMode.LESS_THEN, "lon", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andLonLessThanOrEqualTo(java.lang.String value) {
          addCriterion("lon", value, ConditionMode.LESS_EQUAL, "lon", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andLonBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("lon", value1, value2, ConditionMode.BETWEEN, "lon", "java.lang.String", "String");
    	  return this;
      }

      public CatalogueSampleCriteria andLonNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("lon", value1, value2, ConditionMode.NOT_BETWEEN, "lon", "java.lang.String", "String");
          return this;
      }
        
      public CatalogueSampleCriteria andLonIn(List<java.lang.String> values) {
          addCriterion("lon", values, ConditionMode.IN, "lon", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andLonNotIn(List<java.lang.String> values) {
          addCriterion("lon", values, ConditionMode.NOT_IN, "lon", "java.lang.String", "String");
          return this;
      }
	public CatalogueSampleCriteria andLatIsNull() {
		isnull("lat");
		return this;
	}
	
	public CatalogueSampleCriteria andLatIsNotNull() {
		notNull("lat");
		return this;
	}
	
	public CatalogueSampleCriteria andLatIsEmpty() {
		empty("lat");
		return this;
	}

	public CatalogueSampleCriteria andLatIsNotEmpty() {
		notEmpty("lat");
		return this;
	}
        public CatalogueSampleCriteria andLatLike(java.lang.String value) {
    	   addCriterion("lat", value, ConditionMode.FUZZY, "lat", "java.lang.String", "String");
    	   return this;
      }

      public CatalogueSampleCriteria andLatNotLike(java.lang.String value) {
          addCriterion("lat", value, ConditionMode.NOT_FUZZY, "lat", "java.lang.String", "String");
          return this;
      }
      public CatalogueSampleCriteria andLatEqualTo(java.lang.String value) {
          addCriterion("lat", value, ConditionMode.EQUAL, "lat", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andLatNotEqualTo(java.lang.String value) {
          addCriterion("lat", value, ConditionMode.NOT_EQUAL, "lat", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andLatGreaterThan(java.lang.String value) {
          addCriterion("lat", value, ConditionMode.GREATER_THEN, "lat", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andLatGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("lat", value, ConditionMode.GREATER_EQUAL, "lat", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andLatLessThan(java.lang.String value) {
          addCriterion("lat", value, ConditionMode.LESS_THEN, "lat", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andLatLessThanOrEqualTo(java.lang.String value) {
          addCriterion("lat", value, ConditionMode.LESS_EQUAL, "lat", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andLatBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("lat", value1, value2, ConditionMode.BETWEEN, "lat", "java.lang.String", "String");
    	  return this;
      }

      public CatalogueSampleCriteria andLatNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("lat", value1, value2, ConditionMode.NOT_BETWEEN, "lat", "java.lang.String", "String");
          return this;
      }
        
      public CatalogueSampleCriteria andLatIn(List<java.lang.String> values) {
          addCriterion("lat", values, ConditionMode.IN, "lat", "java.lang.String", "String");
          return this;
      }

      public CatalogueSampleCriteria andLatNotIn(List<java.lang.String> values) {
          addCriterion("lat", values, ConditionMode.NOT_IN, "lat", "java.lang.String", "String");
          return this;
      }
	public CatalogueSampleCriteria andTimeIsNull() {
		isnull("time");
		return this;
	}
	
	public CatalogueSampleCriteria andTimeIsNotNull() {
		notNull("time");
		return this;
	}
	
	public CatalogueSampleCriteria andTimeIsEmpty() {
		empty("time");
		return this;
	}

	public CatalogueSampleCriteria andTimeIsNotEmpty() {
		notEmpty("time");
		return this;
	}
       public CatalogueSampleCriteria andTimeEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public CatalogueSampleCriteria andTimeNotEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.NOT_EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public CatalogueSampleCriteria andTimeGreaterThan(java.util.Date value) {
          addCriterion("time", value, ConditionMode.GREATER_THEN, "time", "java.util.Date", "String");
          return this;
      }

      public CatalogueSampleCriteria andTimeGreaterThanOrEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.GREATER_EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public CatalogueSampleCriteria andTimeLessThan(java.util.Date value) {
          addCriterion("time", value, ConditionMode.LESS_THEN, "time", "java.util.Date", "String");
          return this;
      }

      public CatalogueSampleCriteria andTimeLessThanOrEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.LESS_EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public CatalogueSampleCriteria andTimeBetween(java.util.Date value1, java.util.Date value2) {
    	  addCriterion("time", value1, value2, ConditionMode.BETWEEN, "time", "java.util.Date", "String");
    	  return this;
      }

      public CatalogueSampleCriteria andTimeNotBetween(java.util.Date value1, java.util.Date value2) {
          addCriterion("time", value1, value2, ConditionMode.NOT_BETWEEN, "time", "java.util.Date", "String");
          return this;
      }
        
      public CatalogueSampleCriteria andTimeIn(List<java.util.Date> values) {
          addCriterion("time", values, ConditionMode.IN, "time", "java.util.Date", "String");
          return this;
      }

      public CatalogueSampleCriteria andTimeNotIn(List<java.util.Date> values) {
          addCriterion("time", values, ConditionMode.NOT_IN, "time", "java.util.Date", "String");
          return this;
      }
	public CatalogueSampleCriteria andTypeIsNull() {
		isnull("type");
		return this;
	}
	
	public CatalogueSampleCriteria andTypeIsNotNull() {
		notNull("type");
		return this;
	}
	
	public CatalogueSampleCriteria andTypeIsEmpty() {
		empty("type");
		return this;
	}

	public CatalogueSampleCriteria andTypeIsNotEmpty() {
		notEmpty("type");
		return this;
	}
       public CatalogueSampleCriteria andTypeEqualTo(java.lang.Integer value) {
          addCriterion("type", value, ConditionMode.EQUAL, "type", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueSampleCriteria andTypeNotEqualTo(java.lang.Integer value) {
          addCriterion("type", value, ConditionMode.NOT_EQUAL, "type", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueSampleCriteria andTypeGreaterThan(java.lang.Integer value) {
          addCriterion("type", value, ConditionMode.GREATER_THEN, "type", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueSampleCriteria andTypeGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("type", value, ConditionMode.GREATER_EQUAL, "type", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueSampleCriteria andTypeLessThan(java.lang.Integer value) {
          addCriterion("type", value, ConditionMode.LESS_THEN, "type", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueSampleCriteria andTypeLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("type", value, ConditionMode.LESS_EQUAL, "type", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueSampleCriteria andTypeBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("type", value1, value2, ConditionMode.BETWEEN, "type", "java.lang.Integer", "Float");
    	  return this;
      }

      public CatalogueSampleCriteria andTypeNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("type", value1, value2, ConditionMode.NOT_BETWEEN, "type", "java.lang.Integer", "Float");
          return this;
      }
        
      public CatalogueSampleCriteria andTypeIn(List<java.lang.Integer> values) {
          addCriterion("type", values, ConditionMode.IN, "type", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueSampleCriteria andTypeNotIn(List<java.lang.Integer> values) {
          addCriterion("type", values, ConditionMode.NOT_IN, "type", "java.lang.Integer", "Float");
          return this;
      }
	public CatalogueSampleCriteria andStatusIsNull() {
		isnull("status");
		return this;
	}
	
	public CatalogueSampleCriteria andStatusIsNotNull() {
		notNull("status");
		return this;
	}
	
	public CatalogueSampleCriteria andStatusIsEmpty() {
		empty("status");
		return this;
	}

	public CatalogueSampleCriteria andStatusIsNotEmpty() {
		notEmpty("status");
		return this;
	}
       public CatalogueSampleCriteria andStatusEqualTo(java.lang.Integer value) {
          addCriterion("status", value, ConditionMode.EQUAL, "status", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueSampleCriteria andStatusNotEqualTo(java.lang.Integer value) {
          addCriterion("status", value, ConditionMode.NOT_EQUAL, "status", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueSampleCriteria andStatusGreaterThan(java.lang.Integer value) {
          addCriterion("status", value, ConditionMode.GREATER_THEN, "status", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueSampleCriteria andStatusGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("status", value, ConditionMode.GREATER_EQUAL, "status", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueSampleCriteria andStatusLessThan(java.lang.Integer value) {
          addCriterion("status", value, ConditionMode.LESS_THEN, "status", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueSampleCriteria andStatusLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("status", value, ConditionMode.LESS_EQUAL, "status", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueSampleCriteria andStatusBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("status", value1, value2, ConditionMode.BETWEEN, "status", "java.lang.Integer", "Float");
    	  return this;
      }

      public CatalogueSampleCriteria andStatusNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("status", value1, value2, ConditionMode.NOT_BETWEEN, "status", "java.lang.Integer", "Float");
          return this;
      }
        
      public CatalogueSampleCriteria andStatusIn(List<java.lang.Integer> values) {
          addCriterion("status", values, ConditionMode.IN, "status", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueSampleCriteria andStatusNotIn(List<java.lang.Integer> values) {
          addCriterion("status", values, ConditionMode.NOT_IN, "status", "java.lang.Integer", "Float");
          return this;
      }
}