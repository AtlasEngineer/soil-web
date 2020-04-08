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
    	return Example.create("catalogue", this);
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
        public CatalogueCriteria andIdLike(java.lang.String value) {
    	   addCriterion("id", value, ConditionMode.FUZZY, "id", "java.lang.String", "String");
    	   return this;
      }

      public CatalogueCriteria andIdNotLike(java.lang.String value) {
          addCriterion("id", value, ConditionMode.NOT_FUZZY, "id", "java.lang.String", "String");
          return this;
      }
      public CatalogueCriteria andIdEqualTo(java.lang.String value) {
          addCriterion("id", value, ConditionMode.EQUAL, "id", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andIdNotEqualTo(java.lang.String value) {
          addCriterion("id", value, ConditionMode.NOT_EQUAL, "id", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andIdGreaterThan(java.lang.String value) {
          addCriterion("id", value, ConditionMode.GREATER_THEN, "id", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andIdGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("id", value, ConditionMode.GREATER_EQUAL, "id", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andIdLessThan(java.lang.String value) {
          addCriterion("id", value, ConditionMode.LESS_THEN, "id", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andIdLessThanOrEqualTo(java.lang.String value) {
          addCriterion("id", value, ConditionMode.LESS_EQUAL, "id", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andIdBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("id", value1, value2, ConditionMode.BETWEEN, "id", "java.lang.String", "String");
    	  return this;
      }

      public CatalogueCriteria andIdNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("id", value1, value2, ConditionMode.NOT_BETWEEN, "id", "java.lang.String", "String");
          return this;
      }
        
      public CatalogueCriteria andIdIn(List<java.lang.String> values) {
          addCriterion("id", values, ConditionMode.IN, "id", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andIdNotIn(List<java.lang.String> values) {
          addCriterion("id", values, ConditionMode.NOT_IN, "id", "java.lang.String", "String");
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
    	   addCriterion("name", value, ConditionMode.FUZZY, "name", "java.lang.String", "String");
    	   return this;
      }

      public CatalogueCriteria andNameNotLike(java.lang.String value) {
          addCriterion("name", value, ConditionMode.NOT_FUZZY, "name", "java.lang.String", "String");
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
	public CatalogueCriteria andEnameIsNull() {
		isnull("ename");
		return this;
	}
	
	public CatalogueCriteria andEnameIsNotNull() {
		notNull("ename");
		return this;
	}
	
	public CatalogueCriteria andEnameIsEmpty() {
		empty("ename");
		return this;
	}

	public CatalogueCriteria andEnameIsNotEmpty() {
		notEmpty("ename");
		return this;
	}
        public CatalogueCriteria andEnameLike(java.lang.String value) {
    	   addCriterion("ename", value, ConditionMode.FUZZY, "ename", "java.lang.String", "String");
    	   return this;
      }

      public CatalogueCriteria andEnameNotLike(java.lang.String value) {
          addCriterion("ename", value, ConditionMode.NOT_FUZZY, "ename", "java.lang.String", "String");
          return this;
      }
      public CatalogueCriteria andEnameEqualTo(java.lang.String value) {
          addCriterion("ename", value, ConditionMode.EQUAL, "ename", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andEnameNotEqualTo(java.lang.String value) {
          addCriterion("ename", value, ConditionMode.NOT_EQUAL, "ename", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andEnameGreaterThan(java.lang.String value) {
          addCriterion("ename", value, ConditionMode.GREATER_THEN, "ename", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andEnameGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("ename", value, ConditionMode.GREATER_EQUAL, "ename", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andEnameLessThan(java.lang.String value) {
          addCriterion("ename", value, ConditionMode.LESS_THEN, "ename", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andEnameLessThanOrEqualTo(java.lang.String value) {
          addCriterion("ename", value, ConditionMode.LESS_EQUAL, "ename", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andEnameBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("ename", value1, value2, ConditionMode.BETWEEN, "ename", "java.lang.String", "String");
    	  return this;
      }

      public CatalogueCriteria andEnameNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("ename", value1, value2, ConditionMode.NOT_BETWEEN, "ename", "java.lang.String", "String");
          return this;
      }
        
      public CatalogueCriteria andEnameIn(List<java.lang.String> values) {
          addCriterion("ename", values, ConditionMode.IN, "ename", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andEnameNotIn(List<java.lang.String> values) {
          addCriterion("ename", values, ConditionMode.NOT_IN, "ename", "java.lang.String", "String");
          return this;
      }
	public CatalogueCriteria andPinyinIsNull() {
		isnull("pinyin");
		return this;
	}
	
	public CatalogueCriteria andPinyinIsNotNull() {
		notNull("pinyin");
		return this;
	}
	
	public CatalogueCriteria andPinyinIsEmpty() {
		empty("pinyin");
		return this;
	}

	public CatalogueCriteria andPinyinIsNotEmpty() {
		notEmpty("pinyin");
		return this;
	}
        public CatalogueCriteria andPinyinLike(java.lang.String value) {
    	   addCriterion("pinyin", value, ConditionMode.FUZZY, "pinyin", "java.lang.String", "String");
    	   return this;
      }

      public CatalogueCriteria andPinyinNotLike(java.lang.String value) {
          addCriterion("pinyin", value, ConditionMode.NOT_FUZZY, "pinyin", "java.lang.String", "String");
          return this;
      }
      public CatalogueCriteria andPinyinEqualTo(java.lang.String value) {
          addCriterion("pinyin", value, ConditionMode.EQUAL, "pinyin", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andPinyinNotEqualTo(java.lang.String value) {
          addCriterion("pinyin", value, ConditionMode.NOT_EQUAL, "pinyin", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andPinyinGreaterThan(java.lang.String value) {
          addCriterion("pinyin", value, ConditionMode.GREATER_THEN, "pinyin", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andPinyinGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("pinyin", value, ConditionMode.GREATER_EQUAL, "pinyin", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andPinyinLessThan(java.lang.String value) {
          addCriterion("pinyin", value, ConditionMode.LESS_THEN, "pinyin", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andPinyinLessThanOrEqualTo(java.lang.String value) {
          addCriterion("pinyin", value, ConditionMode.LESS_EQUAL, "pinyin", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andPinyinBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("pinyin", value1, value2, ConditionMode.BETWEEN, "pinyin", "java.lang.String", "String");
    	  return this;
      }

      public CatalogueCriteria andPinyinNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("pinyin", value1, value2, ConditionMode.NOT_BETWEEN, "pinyin", "java.lang.String", "String");
          return this;
      }
        
      public CatalogueCriteria andPinyinIn(List<java.lang.String> values) {
          addCriterion("pinyin", values, ConditionMode.IN, "pinyin", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andPinyinNotIn(List<java.lang.String> values) {
          addCriterion("pinyin", values, ConditionMode.NOT_IN, "pinyin", "java.lang.String", "String");
          return this;
      }
	public CatalogueCriteria andAboutIsNull() {
		isnull("about");
		return this;
	}
	
	public CatalogueCriteria andAboutIsNotNull() {
		notNull("about");
		return this;
	}
	
	public CatalogueCriteria andAboutIsEmpty() {
		empty("about");
		return this;
	}

	public CatalogueCriteria andAboutIsNotEmpty() {
		notEmpty("about");
		return this;
	}
        public CatalogueCriteria andAboutLike(java.lang.String value) {
    	   addCriterion("about", value, ConditionMode.FUZZY, "about", "java.lang.String", "String");
    	   return this;
      }

      public CatalogueCriteria andAboutNotLike(java.lang.String value) {
          addCriterion("about", value, ConditionMode.NOT_FUZZY, "about", "java.lang.String", "String");
          return this;
      }
      public CatalogueCriteria andAboutEqualTo(java.lang.String value) {
          addCriterion("about", value, ConditionMode.EQUAL, "about", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andAboutNotEqualTo(java.lang.String value) {
          addCriterion("about", value, ConditionMode.NOT_EQUAL, "about", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andAboutGreaterThan(java.lang.String value) {
          addCriterion("about", value, ConditionMode.GREATER_THEN, "about", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andAboutGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("about", value, ConditionMode.GREATER_EQUAL, "about", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andAboutLessThan(java.lang.String value) {
          addCriterion("about", value, ConditionMode.LESS_THEN, "about", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andAboutLessThanOrEqualTo(java.lang.String value) {
          addCriterion("about", value, ConditionMode.LESS_EQUAL, "about", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andAboutBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("about", value1, value2, ConditionMode.BETWEEN, "about", "java.lang.String", "String");
    	  return this;
      }

      public CatalogueCriteria andAboutNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("about", value1, value2, ConditionMode.NOT_BETWEEN, "about", "java.lang.String", "String");
          return this;
      }
        
      public CatalogueCriteria andAboutIn(List<java.lang.String> values) {
          addCriterion("about", values, ConditionMode.IN, "about", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andAboutNotIn(List<java.lang.String> values) {
          addCriterion("about", values, ConditionMode.NOT_IN, "about", "java.lang.String", "String");
          return this;
      }
	public CatalogueCriteria andFeaturesIsNull() {
		isnull("features");
		return this;
	}
	
	public CatalogueCriteria andFeaturesIsNotNull() {
		notNull("features");
		return this;
	}
	
	public CatalogueCriteria andFeaturesIsEmpty() {
		empty("features");
		return this;
	}

	public CatalogueCriteria andFeaturesIsNotEmpty() {
		notEmpty("features");
		return this;
	}
        public CatalogueCriteria andFeaturesLike(java.lang.String value) {
    	   addCriterion("features", value, ConditionMode.FUZZY, "features", "java.lang.String", "String");
    	   return this;
      }

      public CatalogueCriteria andFeaturesNotLike(java.lang.String value) {
          addCriterion("features", value, ConditionMode.NOT_FUZZY, "features", "java.lang.String", "String");
          return this;
      }
      public CatalogueCriteria andFeaturesEqualTo(java.lang.String value) {
          addCriterion("features", value, ConditionMode.EQUAL, "features", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andFeaturesNotEqualTo(java.lang.String value) {
          addCriterion("features", value, ConditionMode.NOT_EQUAL, "features", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andFeaturesGreaterThan(java.lang.String value) {
          addCriterion("features", value, ConditionMode.GREATER_THEN, "features", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andFeaturesGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("features", value, ConditionMode.GREATER_EQUAL, "features", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andFeaturesLessThan(java.lang.String value) {
          addCriterion("features", value, ConditionMode.LESS_THEN, "features", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andFeaturesLessThanOrEqualTo(java.lang.String value) {
          addCriterion("features", value, ConditionMode.LESS_EQUAL, "features", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andFeaturesBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("features", value1, value2, ConditionMode.BETWEEN, "features", "java.lang.String", "String");
    	  return this;
      }

      public CatalogueCriteria andFeaturesNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("features", value1, value2, ConditionMode.NOT_BETWEEN, "features", "java.lang.String", "String");
          return this;
      }
        
      public CatalogueCriteria andFeaturesIn(List<java.lang.String> values) {
          addCriterion("features", values, ConditionMode.IN, "features", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andFeaturesNotIn(List<java.lang.String> values) {
          addCriterion("features", values, ConditionMode.NOT_IN, "features", "java.lang.String", "String");
          return this;
      }
	public CatalogueCriteria andPurposeIsNull() {
		isnull("purpose");
		return this;
	}
	
	public CatalogueCriteria andPurposeIsNotNull() {
		notNull("purpose");
		return this;
	}
	
	public CatalogueCriteria andPurposeIsEmpty() {
		empty("purpose");
		return this;
	}

	public CatalogueCriteria andPurposeIsNotEmpty() {
		notEmpty("purpose");
		return this;
	}
        public CatalogueCriteria andPurposeLike(java.lang.String value) {
    	   addCriterion("purpose", value, ConditionMode.FUZZY, "purpose", "java.lang.String", "String");
    	   return this;
      }

      public CatalogueCriteria andPurposeNotLike(java.lang.String value) {
          addCriterion("purpose", value, ConditionMode.NOT_FUZZY, "purpose", "java.lang.String", "String");
          return this;
      }
      public CatalogueCriteria andPurposeEqualTo(java.lang.String value) {
          addCriterion("purpose", value, ConditionMode.EQUAL, "purpose", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andPurposeNotEqualTo(java.lang.String value) {
          addCriterion("purpose", value, ConditionMode.NOT_EQUAL, "purpose", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andPurposeGreaterThan(java.lang.String value) {
          addCriterion("purpose", value, ConditionMode.GREATER_THEN, "purpose", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andPurposeGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("purpose", value, ConditionMode.GREATER_EQUAL, "purpose", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andPurposeLessThan(java.lang.String value) {
          addCriterion("purpose", value, ConditionMode.LESS_THEN, "purpose", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andPurposeLessThanOrEqualTo(java.lang.String value) {
          addCriterion("purpose", value, ConditionMode.LESS_EQUAL, "purpose", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andPurposeBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("purpose", value1, value2, ConditionMode.BETWEEN, "purpose", "java.lang.String", "String");
    	  return this;
      }

      public CatalogueCriteria andPurposeNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("purpose", value1, value2, ConditionMode.NOT_BETWEEN, "purpose", "java.lang.String", "String");
          return this;
      }
        
      public CatalogueCriteria andPurposeIn(List<java.lang.String> values) {
          addCriterion("purpose", values, ConditionMode.IN, "purpose", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andPurposeNotIn(List<java.lang.String> values) {
          addCriterion("purpose", values, ConditionMode.NOT_IN, "purpose", "java.lang.String", "String");
          return this;
      }
	public CatalogueCriteria andDelIsNull() {
		isnull("del");
		return this;
	}
	
	public CatalogueCriteria andDelIsNotNull() {
		notNull("del");
		return this;
	}
	
	public CatalogueCriteria andDelIsEmpty() {
		empty("del");
		return this;
	}

	public CatalogueCriteria andDelIsNotEmpty() {
		notEmpty("del");
		return this;
	}
       public CatalogueCriteria andDelEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueCriteria andDelNotEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.NOT_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueCriteria andDelGreaterThan(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.GREATER_THEN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueCriteria andDelGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.GREATER_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueCriteria andDelLessThan(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.LESS_THEN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueCriteria andDelLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.LESS_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueCriteria andDelBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("del", value1, value2, ConditionMode.BETWEEN, "del", "java.lang.Integer", "Float");
    	  return this;
      }

      public CatalogueCriteria andDelNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("del", value1, value2, ConditionMode.NOT_BETWEEN, "del", "java.lang.Integer", "Float");
          return this;
      }
        
      public CatalogueCriteria andDelIn(List<java.lang.Integer> values) {
          addCriterion("del", values, ConditionMode.IN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueCriteria andDelNotIn(List<java.lang.Integer> values) {
          addCriterion("del", values, ConditionMode.NOT_IN, "del", "java.lang.Integer", "Float");
          return this;
      }
	public CatalogueCriteria andCIdIsNull() {
		isnull("c_id");
		return this;
	}
	
	public CatalogueCriteria andCIdIsNotNull() {
		notNull("c_id");
		return this;
	}
	
	public CatalogueCriteria andCIdIsEmpty() {
		empty("c_id");
		return this;
	}

	public CatalogueCriteria andCIdIsNotEmpty() {
		notEmpty("c_id");
		return this;
	}
       public CatalogueCriteria andCIdEqualTo(java.lang.Integer value) {
          addCriterion("c_id", value, ConditionMode.EQUAL, "cId", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueCriteria andCIdNotEqualTo(java.lang.Integer value) {
          addCriterion("c_id", value, ConditionMode.NOT_EQUAL, "cId", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueCriteria andCIdGreaterThan(java.lang.Integer value) {
          addCriterion("c_id", value, ConditionMode.GREATER_THEN, "cId", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueCriteria andCIdGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("c_id", value, ConditionMode.GREATER_EQUAL, "cId", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueCriteria andCIdLessThan(java.lang.Integer value) {
          addCriterion("c_id", value, ConditionMode.LESS_THEN, "cId", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueCriteria andCIdLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("c_id", value, ConditionMode.LESS_EQUAL, "cId", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueCriteria andCIdBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("c_id", value1, value2, ConditionMode.BETWEEN, "cId", "java.lang.Integer", "Float");
    	  return this;
      }

      public CatalogueCriteria andCIdNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("c_id", value1, value2, ConditionMode.NOT_BETWEEN, "cId", "java.lang.Integer", "Float");
          return this;
      }
        
      public CatalogueCriteria andCIdIn(List<java.lang.Integer> values) {
          addCriterion("c_id", values, ConditionMode.IN, "cId", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueCriteria andCIdNotIn(List<java.lang.Integer> values) {
          addCriterion("c_id", values, ConditionMode.NOT_IN, "cId", "java.lang.Integer", "Float");
          return this;
      }
	public CatalogueCriteria andBotanyTypeIsNull() {
		isnull("botany_type");
		return this;
	}
	
	public CatalogueCriteria andBotanyTypeIsNotNull() {
		notNull("botany_type");
		return this;
	}
	
	public CatalogueCriteria andBotanyTypeIsEmpty() {
		empty("botany_type");
		return this;
	}

	public CatalogueCriteria andBotanyTypeIsNotEmpty() {
		notEmpty("botany_type");
		return this;
	}
       public CatalogueCriteria andBotanyTypeEqualTo(java.lang.Integer value) {
          addCriterion("botany_type", value, ConditionMode.EQUAL, "botanyType", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueCriteria andBotanyTypeNotEqualTo(java.lang.Integer value) {
          addCriterion("botany_type", value, ConditionMode.NOT_EQUAL, "botanyType", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueCriteria andBotanyTypeGreaterThan(java.lang.Integer value) {
          addCriterion("botany_type", value, ConditionMode.GREATER_THEN, "botanyType", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueCriteria andBotanyTypeGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("botany_type", value, ConditionMode.GREATER_EQUAL, "botanyType", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueCriteria andBotanyTypeLessThan(java.lang.Integer value) {
          addCriterion("botany_type", value, ConditionMode.LESS_THEN, "botanyType", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueCriteria andBotanyTypeLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("botany_type", value, ConditionMode.LESS_EQUAL, "botanyType", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueCriteria andBotanyTypeBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("botany_type", value1, value2, ConditionMode.BETWEEN, "botanyType", "java.lang.Integer", "Float");
    	  return this;
      }

      public CatalogueCriteria andBotanyTypeNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("botany_type", value1, value2, ConditionMode.NOT_BETWEEN, "botanyType", "java.lang.Integer", "Float");
          return this;
      }
        
      public CatalogueCriteria andBotanyTypeIn(List<java.lang.Integer> values) {
          addCriterion("botany_type", values, ConditionMode.IN, "botanyType", "java.lang.Integer", "Float");
          return this;
      }

      public CatalogueCriteria andBotanyTypeNotIn(List<java.lang.Integer> values) {
          addCriterion("botany_type", values, ConditionMode.NOT_IN, "botanyType", "java.lang.Integer", "Float");
          return this;
      }
	public CatalogueCriteria andImageIsNull() {
		isnull("image");
		return this;
	}
	
	public CatalogueCriteria andImageIsNotNull() {
		notNull("image");
		return this;
	}
	
	public CatalogueCriteria andImageIsEmpty() {
		empty("image");
		return this;
	}

	public CatalogueCriteria andImageIsNotEmpty() {
		notEmpty("image");
		return this;
	}
        public CatalogueCriteria andImageLike(java.lang.String value) {
    	   addCriterion("image", value, ConditionMode.FUZZY, "image", "java.lang.String", "Float");
    	   return this;
      }

      public CatalogueCriteria andImageNotLike(java.lang.String value) {
          addCriterion("image", value, ConditionMode.NOT_FUZZY, "image", "java.lang.String", "Float");
          return this;
      }
      public CatalogueCriteria andImageEqualTo(java.lang.String value) {
          addCriterion("image", value, ConditionMode.EQUAL, "image", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andImageNotEqualTo(java.lang.String value) {
          addCriterion("image", value, ConditionMode.NOT_EQUAL, "image", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andImageGreaterThan(java.lang.String value) {
          addCriterion("image", value, ConditionMode.GREATER_THEN, "image", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andImageGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("image", value, ConditionMode.GREATER_EQUAL, "image", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andImageLessThan(java.lang.String value) {
          addCriterion("image", value, ConditionMode.LESS_THEN, "image", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andImageLessThanOrEqualTo(java.lang.String value) {
          addCriterion("image", value, ConditionMode.LESS_EQUAL, "image", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andImageBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("image", value1, value2, ConditionMode.BETWEEN, "image", "java.lang.String", "String");
    	  return this;
      }

      public CatalogueCriteria andImageNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("image", value1, value2, ConditionMode.NOT_BETWEEN, "image", "java.lang.String", "String");
          return this;
      }
        
      public CatalogueCriteria andImageIn(List<java.lang.String> values) {
          addCriterion("image", values, ConditionMode.IN, "image", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andImageNotIn(List<java.lang.String> values) {
          addCriterion("image", values, ConditionMode.NOT_IN, "image", "java.lang.String", "String");
          return this;
      }
	public CatalogueCriteria andAddressIsNull() {
		isnull("address");
		return this;
	}
	
	public CatalogueCriteria andAddressIsNotNull() {
		notNull("address");
		return this;
	}
	
	public CatalogueCriteria andAddressIsEmpty() {
		empty("address");
		return this;
	}

	public CatalogueCriteria andAddressIsNotEmpty() {
		notEmpty("address");
		return this;
	}
        public CatalogueCriteria andAddressLike(java.lang.String value) {
    	   addCriterion("address", value, ConditionMode.FUZZY, "address", "java.lang.String", "String");
    	   return this;
      }

      public CatalogueCriteria andAddressNotLike(java.lang.String value) {
          addCriterion("address", value, ConditionMode.NOT_FUZZY, "address", "java.lang.String", "String");
          return this;
      }
      public CatalogueCriteria andAddressEqualTo(java.lang.String value) {
          addCriterion("address", value, ConditionMode.EQUAL, "address", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andAddressNotEqualTo(java.lang.String value) {
          addCriterion("address", value, ConditionMode.NOT_EQUAL, "address", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andAddressGreaterThan(java.lang.String value) {
          addCriterion("address", value, ConditionMode.GREATER_THEN, "address", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andAddressGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("address", value, ConditionMode.GREATER_EQUAL, "address", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andAddressLessThan(java.lang.String value) {
          addCriterion("address", value, ConditionMode.LESS_THEN, "address", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andAddressLessThanOrEqualTo(java.lang.String value) {
          addCriterion("address", value, ConditionMode.LESS_EQUAL, "address", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andAddressBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("address", value1, value2, ConditionMode.BETWEEN, "address", "java.lang.String", "String");
    	  return this;
      }

      public CatalogueCriteria andAddressNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("address", value1, value2, ConditionMode.NOT_BETWEEN, "address", "java.lang.String", "String");
          return this;
      }
        
      public CatalogueCriteria andAddressIn(List<java.lang.String> values) {
          addCriterion("address", values, ConditionMode.IN, "address", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andAddressNotIn(List<java.lang.String> values) {
          addCriterion("address", values, ConditionMode.NOT_IN, "address", "java.lang.String", "String");
          return this;
      }
	public CatalogueCriteria andValueIsNull() {
		isnull("value");
		return this;
	}
	
	public CatalogueCriteria andValueIsNotNull() {
		notNull("value");
		return this;
	}
	
	public CatalogueCriteria andValueIsEmpty() {
		empty("value");
		return this;
	}

	public CatalogueCriteria andValueIsNotEmpty() {
		notEmpty("value");
		return this;
	}
        public CatalogueCriteria andValueLike(java.lang.String value) {
    	   addCriterion("value", value, ConditionMode.FUZZY, "value", "java.lang.String", "String");
    	   return this;
      }

      public CatalogueCriteria andValueNotLike(java.lang.String value) {
          addCriterion("value", value, ConditionMode.NOT_FUZZY, "value", "java.lang.String", "String");
          return this;
      }
      public CatalogueCriteria andValueEqualTo(java.lang.String value) {
          addCriterion("value", value, ConditionMode.EQUAL, "value", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andValueNotEqualTo(java.lang.String value) {
          addCriterion("value", value, ConditionMode.NOT_EQUAL, "value", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andValueGreaterThan(java.lang.String value) {
          addCriterion("value", value, ConditionMode.GREATER_THEN, "value", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andValueGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("value", value, ConditionMode.GREATER_EQUAL, "value", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andValueLessThan(java.lang.String value) {
          addCriterion("value", value, ConditionMode.LESS_THEN, "value", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andValueLessThanOrEqualTo(java.lang.String value) {
          addCriterion("value", value, ConditionMode.LESS_EQUAL, "value", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andValueBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("value", value1, value2, ConditionMode.BETWEEN, "value", "java.lang.String", "String");
    	  return this;
      }

      public CatalogueCriteria andValueNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("value", value1, value2, ConditionMode.NOT_BETWEEN, "value", "java.lang.String", "String");
          return this;
      }
        
      public CatalogueCriteria andValueIn(List<java.lang.String> values) {
          addCriterion("value", values, ConditionMode.IN, "value", "java.lang.String", "String");
          return this;
      }

      public CatalogueCriteria andValueNotIn(List<java.lang.String> values) {
          addCriterion("value", values, ConditionMode.NOT_IN, "value", "java.lang.String", "String");
          return this;
      }
}