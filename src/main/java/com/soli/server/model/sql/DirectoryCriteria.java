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
 * @date 2020-09-19
 * @version 1.0
 * @since 1.0
 */
public class DirectoryCriteria extends Columns implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static DirectoryCriteria create() {
		return new DirectoryCriteria();
	}
	
	public static DirectoryCriteria create(Column column) {
		DirectoryCriteria that = new DirectoryCriteria();
		that.add(column);
        return that;
    }

    public static DirectoryCriteria create(String name, Object value) {
        return (DirectoryCriteria) create().eq(name, value);
    }
    
    public Example example() {
    	return Example.create("tr_directory", this);
    }
    
    /**
     * equals
     *
     * @param name
     * @param value
     * @return
     */
    public DirectoryCriteria eq(String name, Object value) {
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
    public DirectoryCriteria ne(String name, Object value) {
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

    public DirectoryCriteria like(String name, Object value) {
    	super.like(name, value);
        return this;
    }
    
    public DirectoryCriteria notLike(String name, Object value) {
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
    public DirectoryCriteria gt(String name, Object value) {
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
    public DirectoryCriteria ge(String name, Object value) {
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
    public DirectoryCriteria lt(String name, Object value) {
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
    public DirectoryCriteria le(String name, Object value) {
    	super.le(name, value);
        return this;
    }
    
    public DirectoryCriteria isnull(String name) {
    	super.isnull(name);
        return this;
    } 

    public DirectoryCriteria notNull(String name) {
    	super.notNull(name);
        return this;
    } 
    
    public DirectoryCriteria empty(String name) {
    	super.empty(name);
        return this;
    } 
    
    public DirectoryCriteria notEmpty(String name) {
    	super.notEmpty(name);
        return this;
    } 
    
    public DirectoryCriteria add(Column column) {
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
		 
	public DirectoryCriteria andIdIsNull() {
		isnull("id");
		return this;
	}
	
	public DirectoryCriteria andIdIsNotNull() {
		notNull("id");
		return this;
	}
	
	public DirectoryCriteria andIdIsEmpty() {
		empty("id");
		return this;
	}

	public DirectoryCriteria andIdIsNotEmpty() {
		notEmpty("id");
		return this;
	}
       public DirectoryCriteria andIdEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public DirectoryCriteria andIdNotEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.NOT_EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public DirectoryCriteria andIdGreaterThan(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.GREATER_THEN, "id", "java.lang.Integer", "Float");
          return this;
      }

      public DirectoryCriteria andIdGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.GREATER_EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public DirectoryCriteria andIdLessThan(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.LESS_THEN, "id", "java.lang.Integer", "Float");
          return this;
      }

      public DirectoryCriteria andIdLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.LESS_EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public DirectoryCriteria andIdBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("id", value1, value2, ConditionMode.BETWEEN, "id", "java.lang.Integer", "Float");
    	  return this;
      }

      public DirectoryCriteria andIdNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("id", value1, value2, ConditionMode.NOT_BETWEEN, "id", "java.lang.Integer", "Float");
          return this;
      }
        
      public DirectoryCriteria andIdIn(List<java.lang.Integer> values) {
          addCriterion("id", values, ConditionMode.IN, "id", "java.lang.Integer", "Float");
          return this;
      }

      public DirectoryCriteria andIdNotIn(List<java.lang.Integer> values) {
          addCriterion("id", values, ConditionMode.NOT_IN, "id", "java.lang.Integer", "Float");
          return this;
      }
	public DirectoryCriteria andParentIdIsNull() {
		isnull("parent_id");
		return this;
	}
	
	public DirectoryCriteria andParentIdIsNotNull() {
		notNull("parent_id");
		return this;
	}
	
	public DirectoryCriteria andParentIdIsEmpty() {
		empty("parent_id");
		return this;
	}

	public DirectoryCriteria andParentIdIsNotEmpty() {
		notEmpty("parent_id");
		return this;
	}
       public DirectoryCriteria andParentIdEqualTo(java.lang.Integer value) {
          addCriterion("parent_id", value, ConditionMode.EQUAL, "parentId", "java.lang.Integer", "Float");
          return this;
      }

      public DirectoryCriteria andParentIdNotEqualTo(java.lang.Integer value) {
          addCriterion("parent_id", value, ConditionMode.NOT_EQUAL, "parentId", "java.lang.Integer", "Float");
          return this;
      }

      public DirectoryCriteria andParentIdGreaterThan(java.lang.Integer value) {
          addCriterion("parent_id", value, ConditionMode.GREATER_THEN, "parentId", "java.lang.Integer", "Float");
          return this;
      }

      public DirectoryCriteria andParentIdGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("parent_id", value, ConditionMode.GREATER_EQUAL, "parentId", "java.lang.Integer", "Float");
          return this;
      }

      public DirectoryCriteria andParentIdLessThan(java.lang.Integer value) {
          addCriterion("parent_id", value, ConditionMode.LESS_THEN, "parentId", "java.lang.Integer", "Float");
          return this;
      }

      public DirectoryCriteria andParentIdLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("parent_id", value, ConditionMode.LESS_EQUAL, "parentId", "java.lang.Integer", "Float");
          return this;
      }

      public DirectoryCriteria andParentIdBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("parent_id", value1, value2, ConditionMode.BETWEEN, "parentId", "java.lang.Integer", "Float");
    	  return this;
      }

      public DirectoryCriteria andParentIdNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("parent_id", value1, value2, ConditionMode.NOT_BETWEEN, "parentId", "java.lang.Integer", "Float");
          return this;
      }
        
      public DirectoryCriteria andParentIdIn(List<java.lang.Integer> values) {
          addCriterion("parent_id", values, ConditionMode.IN, "parentId", "java.lang.Integer", "Float");
          return this;
      }

      public DirectoryCriteria andParentIdNotIn(List<java.lang.Integer> values) {
          addCriterion("parent_id", values, ConditionMode.NOT_IN, "parentId", "java.lang.Integer", "Float");
          return this;
      }
	public DirectoryCriteria andLevelIsNull() {
		isnull("level");
		return this;
	}
	
	public DirectoryCriteria andLevelIsNotNull() {
		notNull("level");
		return this;
	}
	
	public DirectoryCriteria andLevelIsEmpty() {
		empty("level");
		return this;
	}

	public DirectoryCriteria andLevelIsNotEmpty() {
		notEmpty("level");
		return this;
	}
       public DirectoryCriteria andLevelEqualTo(java.lang.Integer value) {
          addCriterion("level", value, ConditionMode.EQUAL, "level", "java.lang.Integer", "Float");
          return this;
      }

      public DirectoryCriteria andLevelNotEqualTo(java.lang.Integer value) {
          addCriterion("level", value, ConditionMode.NOT_EQUAL, "level", "java.lang.Integer", "Float");
          return this;
      }

      public DirectoryCriteria andLevelGreaterThan(java.lang.Integer value) {
          addCriterion("level", value, ConditionMode.GREATER_THEN, "level", "java.lang.Integer", "Float");
          return this;
      }

      public DirectoryCriteria andLevelGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("level", value, ConditionMode.GREATER_EQUAL, "level", "java.lang.Integer", "Float");
          return this;
      }

      public DirectoryCriteria andLevelLessThan(java.lang.Integer value) {
          addCriterion("level", value, ConditionMode.LESS_THEN, "level", "java.lang.Integer", "Float");
          return this;
      }

      public DirectoryCriteria andLevelLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("level", value, ConditionMode.LESS_EQUAL, "level", "java.lang.Integer", "Float");
          return this;
      }

      public DirectoryCriteria andLevelBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("level", value1, value2, ConditionMode.BETWEEN, "level", "java.lang.Integer", "Float");
    	  return this;
      }

      public DirectoryCriteria andLevelNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("level", value1, value2, ConditionMode.NOT_BETWEEN, "level", "java.lang.Integer", "Float");
          return this;
      }
        
      public DirectoryCriteria andLevelIn(List<java.lang.Integer> values) {
          addCriterion("level", values, ConditionMode.IN, "level", "java.lang.Integer", "Float");
          return this;
      }

      public DirectoryCriteria andLevelNotIn(List<java.lang.Integer> values) {
          addCriterion("level", values, ConditionMode.NOT_IN, "level", "java.lang.Integer", "Float");
          return this;
      }
	public DirectoryCriteria andNameIsNull() {
		isnull("name");
		return this;
	}
	
	public DirectoryCriteria andNameIsNotNull() {
		notNull("name");
		return this;
	}
	
	public DirectoryCriteria andNameIsEmpty() {
		empty("name");
		return this;
	}

	public DirectoryCriteria andNameIsNotEmpty() {
		notEmpty("name");
		return this;
	}
        public DirectoryCriteria andNameLike(java.lang.String value) {
    	   addCriterion("name", value, ConditionMode.FUZZY, "name", "java.lang.String", "Float");
    	   return this;
      }

      public DirectoryCriteria andNameNotLike(java.lang.String value) {
          addCriterion("name", value, ConditionMode.NOT_FUZZY, "name", "java.lang.String", "Float");
          return this;
      }
      public DirectoryCriteria andNameEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public DirectoryCriteria andNameNotEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.NOT_EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public DirectoryCriteria andNameGreaterThan(java.lang.String value) {
          addCriterion("name", value, ConditionMode.GREATER_THEN, "name", "java.lang.String", "String");
          return this;
      }

      public DirectoryCriteria andNameGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.GREATER_EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public DirectoryCriteria andNameLessThan(java.lang.String value) {
          addCriterion("name", value, ConditionMode.LESS_THEN, "name", "java.lang.String", "String");
          return this;
      }

      public DirectoryCriteria andNameLessThanOrEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.LESS_EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public DirectoryCriteria andNameBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("name", value1, value2, ConditionMode.BETWEEN, "name", "java.lang.String", "String");
    	  return this;
      }

      public DirectoryCriteria andNameNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("name", value1, value2, ConditionMode.NOT_BETWEEN, "name", "java.lang.String", "String");
          return this;
      }
        
      public DirectoryCriteria andNameIn(List<java.lang.String> values) {
          addCriterion("name", values, ConditionMode.IN, "name", "java.lang.String", "String");
          return this;
      }

      public DirectoryCriteria andNameNotIn(List<java.lang.String> values) {
          addCriterion("name", values, ConditionMode.NOT_IN, "name", "java.lang.String", "String");
          return this;
      }
	public DirectoryCriteria andCreateTimeIsNull() {
		isnull("create_time");
		return this;
	}
	
	public DirectoryCriteria andCreateTimeIsNotNull() {
		notNull("create_time");
		return this;
	}
	
	public DirectoryCriteria andCreateTimeIsEmpty() {
		empty("create_time");
		return this;
	}

	public DirectoryCriteria andCreateTimeIsNotEmpty() {
		notEmpty("create_time");
		return this;
	}
       public DirectoryCriteria andCreateTimeEqualTo(java.util.Date value) {
          addCriterion("create_time", value, ConditionMode.EQUAL, "createTime", "java.util.Date", "String");
          return this;
      }

      public DirectoryCriteria andCreateTimeNotEqualTo(java.util.Date value) {
          addCriterion("create_time", value, ConditionMode.NOT_EQUAL, "createTime", "java.util.Date", "String");
          return this;
      }

      public DirectoryCriteria andCreateTimeGreaterThan(java.util.Date value) {
          addCriterion("create_time", value, ConditionMode.GREATER_THEN, "createTime", "java.util.Date", "String");
          return this;
      }

      public DirectoryCriteria andCreateTimeGreaterThanOrEqualTo(java.util.Date value) {
          addCriterion("create_time", value, ConditionMode.GREATER_EQUAL, "createTime", "java.util.Date", "String");
          return this;
      }

      public DirectoryCriteria andCreateTimeLessThan(java.util.Date value) {
          addCriterion("create_time", value, ConditionMode.LESS_THEN, "createTime", "java.util.Date", "String");
          return this;
      }

      public DirectoryCriteria andCreateTimeLessThanOrEqualTo(java.util.Date value) {
          addCriterion("create_time", value, ConditionMode.LESS_EQUAL, "createTime", "java.util.Date", "String");
          return this;
      }

      public DirectoryCriteria andCreateTimeBetween(java.util.Date value1, java.util.Date value2) {
    	  addCriterion("create_time", value1, value2, ConditionMode.BETWEEN, "createTime", "java.util.Date", "String");
    	  return this;
      }

      public DirectoryCriteria andCreateTimeNotBetween(java.util.Date value1, java.util.Date value2) {
          addCriterion("create_time", value1, value2, ConditionMode.NOT_BETWEEN, "createTime", "java.util.Date", "String");
          return this;
      }
        
      public DirectoryCriteria andCreateTimeIn(List<java.util.Date> values) {
          addCriterion("create_time", values, ConditionMode.IN, "createTime", "java.util.Date", "String");
          return this;
      }

      public DirectoryCriteria andCreateTimeNotIn(List<java.util.Date> values) {
          addCriterion("create_time", values, ConditionMode.NOT_IN, "createTime", "java.util.Date", "String");
          return this;
      }
	public DirectoryCriteria andDelIsNull() {
		isnull("del");
		return this;
	}
	
	public DirectoryCriteria andDelIsNotNull() {
		notNull("del");
		return this;
	}
	
	public DirectoryCriteria andDelIsEmpty() {
		empty("del");
		return this;
	}

	public DirectoryCriteria andDelIsNotEmpty() {
		notEmpty("del");
		return this;
	}
        public DirectoryCriteria andDelLike(java.lang.String value) {
    	   addCriterion("del", value, ConditionMode.FUZZY, "del", "java.lang.String", "String");
    	   return this;
      }

      public DirectoryCriteria andDelNotLike(java.lang.String value) {
          addCriterion("del", value, ConditionMode.NOT_FUZZY, "del", "java.lang.String", "String");
          return this;
      }
      public DirectoryCriteria andDelEqualTo(java.lang.String value) {
          addCriterion("del", value, ConditionMode.EQUAL, "del", "java.lang.String", "String");
          return this;
      }

      public DirectoryCriteria andDelNotEqualTo(java.lang.String value) {
          addCriterion("del", value, ConditionMode.NOT_EQUAL, "del", "java.lang.String", "String");
          return this;
      }

      public DirectoryCriteria andDelGreaterThan(java.lang.String value) {
          addCriterion("del", value, ConditionMode.GREATER_THEN, "del", "java.lang.String", "String");
          return this;
      }

      public DirectoryCriteria andDelGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("del", value, ConditionMode.GREATER_EQUAL, "del", "java.lang.String", "String");
          return this;
      }

      public DirectoryCriteria andDelLessThan(java.lang.String value) {
          addCriterion("del", value, ConditionMode.LESS_THEN, "del", "java.lang.String", "String");
          return this;
      }

      public DirectoryCriteria andDelLessThanOrEqualTo(java.lang.String value) {
          addCriterion("del", value, ConditionMode.LESS_EQUAL, "del", "java.lang.String", "String");
          return this;
      }

      public DirectoryCriteria andDelBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("del", value1, value2, ConditionMode.BETWEEN, "del", "java.lang.String", "String");
    	  return this;
      }

      public DirectoryCriteria andDelNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("del", value1, value2, ConditionMode.NOT_BETWEEN, "del", "java.lang.String", "String");
          return this;
      }
        
      public DirectoryCriteria andDelIn(List<java.lang.String> values) {
          addCriterion("del", values, ConditionMode.IN, "del", "java.lang.String", "String");
          return this;
      }

      public DirectoryCriteria andDelNotIn(List<java.lang.String> values) {
          addCriterion("del", values, ConditionMode.NOT_IN, "del", "java.lang.String", "String");
          return this;
      }
}