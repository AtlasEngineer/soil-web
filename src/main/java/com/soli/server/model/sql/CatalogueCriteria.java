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
 * @date 2020-09-26
 * @version 1.0
 * @since 1.0
 */
public class CatalogueCriteria extends Columns implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static CatalogueCriteria create() {
		return new CatalogueCriteria();
	}
	
	public static CatalogueCriteria create(Column column) {
		CatalogueCriteria that = new CatalogueCriteria();
		that.add(column);
        return that;
    }

    public static CatalogueCriteria create(String name, Object value) {
        return (CatalogueCriteria) create().eq(name, value);
    }
    
    public Example example() {
    	return Example.create("tr_catalogue", this);
    }
    
    /**
     * equals
     *
     * @param name
     * @param value
     * @return
     */
    public CatalogueCriteria eq(String name, Object value) {
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
    public CatalogueCriteria ne(String name, Object value) {
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

    public CatalogueCriteria like(String name, Object value) {
    	super.like(name, value);
        return this;
    }
    
    public CatalogueCriteria notLike(String name, Object value) {
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
    public CatalogueCriteria gt(String name, Object value) {
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
    public CatalogueCriteria ge(String name, Object value) {
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
    public CatalogueCriteria lt(String name, Object value) {
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
    public CatalogueCriteria le(String name, Object value) {
    	super.le(name, value);
        return this;
    }
    
    public CatalogueCriteria isnull(String name) {
    	super.isnull(name);
        return this;
    } 

    public CatalogueCriteria notNull(String name) {
    	super.notNull(name);
        return this;
    } 
    
    public CatalogueCriteria empty(String name) {
    	super.empty(name);
        return this;
    } 
    
    public CatalogueCriteria notEmpty(String name) {
    	super.notEmpty(name);
        return this;
    } 
    
    public CatalogueCriteria add(Column column) {
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
		 
	public CatalogueCriteria andIdIsNull() {
		isnull("id");
		return this;
	}
	
	public CatalogueCriteria andIdIsNotNull() {
		notNull("id");
		return this;
	}
	
	public CatalogueCriteria andIdIsEmpty() {
		empty("id");
		return this;
	}

	public CatalogueCriteria andIdIsNotEmpty() {
		notEmpty("id");
		return this;
	}
       public CatalogueCriteria andIdEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueCriteria andIdNotEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.NOT_EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueCriteria andIdGreaterThan(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.GREATER_THEN, "id", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueCriteria andIdGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.GREATER_EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueCriteria andIdLessThan(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.LESS_THEN, "id", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueCriteria andIdLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.LESS_EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueCriteria andIdBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("id", value1, value2, ConditionMode.BETWEEN, "id", "java.lang.Integer", "Float");
    	  return this;
      }

      public CatalogueCriteria andIdNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("id", value1, value2, ConditionMode.NOT_BETWEEN, "id", "java.lang.Integer", "Float");
          return this;
      }
        
      public CatalogueCriteria andIdIn(List<java.lang.Integer> values) {
          addCriterion("id", values, ConditionMode.IN, "id", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueCriteria andIdNotIn(List<java.lang.Integer> values) {
          addCriterion("id", values, ConditionMode.NOT_IN, "id", "java.lang.Integer", "Float");
          return this;
      }
	public CatalogueCriteria andNameIsNull() {
		isnull("name");
		return this;
	}
	
	public CatalogueCriteria andNameIsNotNull() {
		notNull("name");
		return this;
	}
	
	public CatalogueCriteria andNameIsEmpty() {
		empty("name");
		return this;
	}

	public CatalogueCriteria andNameIsNotEmpty() {
		notEmpty("name");
		return this;
	}
        public CatalogueCriteria andNameLike(java.lang.String value) {
    	   addCriterion("name", value, ConditionMode.FUZZY, "name", "java.lang.String", "Float");
    	   return this;
      }

      public CatalogueCriteria andNameNotLike(java.lang.String value) {
          addCriterion("name", value, ConditionMode.NOT_FUZZY, "name", "java.lang.String", "Float");
          return this;
      }
      public CatalogueCriteria andNameEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andNameNotEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.NOT_EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andNameGreaterThan(java.lang.String value) {
          addCriterion("name", value, ConditionMode.GREATER_THEN, "name", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andNameGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.GREATER_EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andNameLessThan(java.lang.String value) {
          addCriterion("name", value, ConditionMode.LESS_THEN, "name", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andNameLessThanOrEqualTo(java.lang.String value) {
          addCriterion("name", value, ConditionMode.LESS_EQUAL, "name", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andNameBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("name", value1, value2, ConditionMode.BETWEEN, "name", "java.lang.String", "String");
    	  return this;
      }

      public CatalogueCriteria andNameNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("name", value1, value2, ConditionMode.NOT_BETWEEN, "name", "java.lang.String", "String");
          return this;
      }
        
      public CatalogueCriteria andNameIn(List<java.lang.String> values) {
          addCriterion("name", values, ConditionMode.IN, "name", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andNameNotIn(List<java.lang.String> values) {
          addCriterion("name", values, ConditionMode.NOT_IN, "name", "java.lang.String", "String");
          return this;
      }
	public CatalogueCriteria andTimeIsNull() {
		isnull("time");
		return this;
	}
	
	public CatalogueCriteria andTimeIsNotNull() {
		notNull("time");
		return this;
	}
	
	public CatalogueCriteria andTimeIsEmpty() {
		empty("time");
		return this;
	}

	public CatalogueCriteria andTimeIsNotEmpty() {
		notEmpty("time");
		return this;
	}
       public CatalogueCriteria andTimeEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public CatalogueCriteria andTimeNotEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.NOT_EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public CatalogueCriteria andTimeGreaterThan(java.util.Date value) {
          addCriterion("time", value, ConditionMode.GREATER_THEN, "time", "java.util.Date", "String");
          return this;
      }

      public CatalogueCriteria andTimeGreaterThanOrEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.GREATER_EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public CatalogueCriteria andTimeLessThan(java.util.Date value) {
          addCriterion("time", value, ConditionMode.LESS_THEN, "time", "java.util.Date", "String");
          return this;
      }

      public CatalogueCriteria andTimeLessThanOrEqualTo(java.util.Date value) {
          addCriterion("time", value, ConditionMode.LESS_EQUAL, "time", "java.util.Date", "String");
          return this;
      }

      public CatalogueCriteria andTimeBetween(java.util.Date value1, java.util.Date value2) {
    	  addCriterion("time", value1, value2, ConditionMode.BETWEEN, "time", "java.util.Date", "String");
    	  return this;
      }

      public CatalogueCriteria andTimeNotBetween(java.util.Date value1, java.util.Date value2) {
          addCriterion("time", value1, value2, ConditionMode.NOT_BETWEEN, "time", "java.util.Date", "String");
          return this;
      }
        
      public CatalogueCriteria andTimeIn(List<java.util.Date> values) {
          addCriterion("time", values, ConditionMode.IN, "time", "java.util.Date", "String");
          return this;
      }

      public CatalogueCriteria andTimeNotIn(List<java.util.Date> values) {
          addCriterion("time", values, ConditionMode.NOT_IN, "time", "java.util.Date", "String");
          return this;
      }
	public CatalogueCriteria andParentIdIsNull() {
		isnull("parent_id");
		return this;
	}
	
	public CatalogueCriteria andParentIdIsNotNull() {
		notNull("parent_id");
		return this;
	}
	
	public CatalogueCriteria andParentIdIsEmpty() {
		empty("parent_id");
		return this;
	}

	public CatalogueCriteria andParentIdIsNotEmpty() {
		notEmpty("parent_id");
		return this;
	}
       public CatalogueCriteria andParentIdEqualTo(java.lang.Integer value) {
          addCriterion("parent_id", value, ConditionMode.EQUAL, "parentId", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueCriteria andParentIdNotEqualTo(java.lang.Integer value) {
          addCriterion("parent_id", value, ConditionMode.NOT_EQUAL, "parentId", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueCriteria andParentIdGreaterThan(java.lang.Integer value) {
          addCriterion("parent_id", value, ConditionMode.GREATER_THEN, "parentId", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueCriteria andParentIdGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("parent_id", value, ConditionMode.GREATER_EQUAL, "parentId", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueCriteria andParentIdLessThan(java.lang.Integer value) {
          addCriterion("parent_id", value, ConditionMode.LESS_THEN, "parentId", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueCriteria andParentIdLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("parent_id", value, ConditionMode.LESS_EQUAL, "parentId", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueCriteria andParentIdBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("parent_id", value1, value2, ConditionMode.BETWEEN, "parentId", "java.lang.Integer", "Float");
    	  return this;
      }

      public CatalogueCriteria andParentIdNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("parent_id", value1, value2, ConditionMode.NOT_BETWEEN, "parentId", "java.lang.Integer", "Float");
          return this;
      }
        
      public CatalogueCriteria andParentIdIn(List<java.lang.Integer> values) {
          addCriterion("parent_id", values, ConditionMode.IN, "parentId", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueCriteria andParentIdNotIn(List<java.lang.Integer> values) {
          addCriterion("parent_id", values, ConditionMode.NOT_IN, "parentId", "java.lang.Integer", "Float");
          return this;
      }
	public CatalogueCriteria andStatusIsNull() {
		isnull("status");
		return this;
	}
	
	public CatalogueCriteria andStatusIsNotNull() {
		notNull("status");
		return this;
	}
	
	public CatalogueCriteria andStatusIsEmpty() {
		empty("status");
		return this;
	}

	public CatalogueCriteria andStatusIsNotEmpty() {
		notEmpty("status");
		return this;
	}
        public CatalogueCriteria andStatusLike(java.lang.String value) {
    	   addCriterion("status", value, ConditionMode.FUZZY, "status", "java.lang.String", "Float");
    	   return this;
      }

      public CatalogueCriteria andStatusNotLike(java.lang.String value) {
          addCriterion("status", value, ConditionMode.NOT_FUZZY, "status", "java.lang.String", "Float");
          return this;
      }
      public CatalogueCriteria andStatusEqualTo(java.lang.String value) {
          addCriterion("status", value, ConditionMode.EQUAL, "status", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andStatusNotEqualTo(java.lang.String value) {
          addCriterion("status", value, ConditionMode.NOT_EQUAL, "status", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andStatusGreaterThan(java.lang.String value) {
          addCriterion("status", value, ConditionMode.GREATER_THEN, "status", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andStatusGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("status", value, ConditionMode.GREATER_EQUAL, "status", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andStatusLessThan(java.lang.String value) {
          addCriterion("status", value, ConditionMode.LESS_THEN, "status", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andStatusLessThanOrEqualTo(java.lang.String value) {
          addCriterion("status", value, ConditionMode.LESS_EQUAL, "status", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andStatusBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("status", value1, value2, ConditionMode.BETWEEN, "status", "java.lang.String", "String");
    	  return this;
      }

      public CatalogueCriteria andStatusNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("status", value1, value2, ConditionMode.NOT_BETWEEN, "status", "java.lang.String", "String");
          return this;
      }
        
      public CatalogueCriteria andStatusIn(List<java.lang.String> values) {
          addCriterion("status", values, ConditionMode.IN, "status", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andStatusNotIn(List<java.lang.String> values) {
          addCriterion("status", values, ConditionMode.NOT_IN, "status", "java.lang.String", "String");
          return this;
      }
	public CatalogueCriteria andGradeIdIsNull() {
		isnull("grade_id");
		return this;
	}
	
	public CatalogueCriteria andGradeIdIsNotNull() {
		notNull("grade_id");
		return this;
	}
	
	public CatalogueCriteria andGradeIdIsEmpty() {
		empty("grade_id");
		return this;
	}

	public CatalogueCriteria andGradeIdIsNotEmpty() {
		notEmpty("grade_id");
		return this;
	}
       public CatalogueCriteria andGradeIdEqualTo(java.lang.Integer value) {
          addCriterion("grade_id", value, ConditionMode.EQUAL, "gradeId", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueCriteria andGradeIdNotEqualTo(java.lang.Integer value) {
          addCriterion("grade_id", value, ConditionMode.NOT_EQUAL, "gradeId", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueCriteria andGradeIdGreaterThan(java.lang.Integer value) {
          addCriterion("grade_id", value, ConditionMode.GREATER_THEN, "gradeId", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueCriteria andGradeIdGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("grade_id", value, ConditionMode.GREATER_EQUAL, "gradeId", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueCriteria andGradeIdLessThan(java.lang.Integer value) {
          addCriterion("grade_id", value, ConditionMode.LESS_THEN, "gradeId", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueCriteria andGradeIdLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("grade_id", value, ConditionMode.LESS_EQUAL, "gradeId", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueCriteria andGradeIdBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("grade_id", value1, value2, ConditionMode.BETWEEN, "gradeId", "java.lang.Integer", "Float");
    	  return this;
      }

      public CatalogueCriteria andGradeIdNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("grade_id", value1, value2, ConditionMode.NOT_BETWEEN, "gradeId", "java.lang.Integer", "Float");
          return this;
      }
        
      public CatalogueCriteria andGradeIdIn(List<java.lang.Integer> values) {
          addCriterion("grade_id", values, ConditionMode.IN, "gradeId", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueCriteria andGradeIdNotIn(List<java.lang.Integer> values) {
          addCriterion("grade_id", values, ConditionMode.NOT_IN, "gradeId", "java.lang.Integer", "Float");
          return this;
      }
}