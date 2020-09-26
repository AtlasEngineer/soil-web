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
public class HnwJgpzCriteria extends Columns implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static HnwJgpzCriteria create() {
		return new HnwJgpzCriteria();
	}
	
	public static HnwJgpzCriteria create(Column column) {
		HnwJgpzCriteria that = new HnwJgpzCriteria();
		that.add(column);
        return that;
    }

    public static HnwJgpzCriteria create(String name, Object value) {
        return (HnwJgpzCriteria) create().eq(name, value);
    }
    
    public Example example() {
    	return Example.create("hnw_jgpz", this);
    }
    
    /**
     * equals
     *
     * @param name
     * @param value
     * @return
     */
    public HnwJgpzCriteria eq(String name, Object value) {
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
    public HnwJgpzCriteria ne(String name, Object value) {
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

    public HnwJgpzCriteria like(String name, Object value) {
    	super.like(name, value);
        return this;
    }
    
    public HnwJgpzCriteria notLike(String name, Object value) {
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
    public HnwJgpzCriteria gt(String name, Object value) {
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
    public HnwJgpzCriteria ge(String name, Object value) {
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
    public HnwJgpzCriteria lt(String name, Object value) {
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
    public HnwJgpzCriteria le(String name, Object value) {
    	super.le(name, value);
        return this;
    }
    
    public HnwJgpzCriteria isnull(String name) {
    	super.isnull(name);
        return this;
    } 

    public HnwJgpzCriteria notNull(String name) {
    	super.notNull(name);
        return this;
    } 
    
    public HnwJgpzCriteria empty(String name) {
    	super.empty(name);
        return this;
    } 
    
    public HnwJgpzCriteria notEmpty(String name) {
    	super.notEmpty(name);
        return this;
    } 
    
    public HnwJgpzCriteria add(Column column) {
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
		 
	public HnwJgpzCriteria andCategoryIsNull() {
		isnull("category");
		return this;
	}
	
	public HnwJgpzCriteria andCategoryIsNotNull() {
		notNull("category");
		return this;
	}
	
	public HnwJgpzCriteria andCategoryIsEmpty() {
		empty("category");
		return this;
	}

	public HnwJgpzCriteria andCategoryIsNotEmpty() {
		notEmpty("category");
		return this;
	}
        public HnwJgpzCriteria andCategoryLike(java.lang.String value) {
    	   addCriterion("category", value, ConditionMode.FUZZY, "category", "java.lang.String", "String");
    	   return this;
      }

      public HnwJgpzCriteria andCategoryNotLike(java.lang.String value) {
          addCriterion("category", value, ConditionMode.NOT_FUZZY, "category", "java.lang.String", "String");
          return this;
      }
      public HnwJgpzCriteria andCategoryEqualTo(java.lang.String value) {
          addCriterion("category", value, ConditionMode.EQUAL, "category", "java.lang.String", "String");
          return this;
      }

      public HnwJgpzCriteria andCategoryNotEqualTo(java.lang.String value) {
          addCriterion("category", value, ConditionMode.NOT_EQUAL, "category", "java.lang.String", "String");
          return this;
      }

      public HnwJgpzCriteria andCategoryGreaterThan(java.lang.String value) {
          addCriterion("category", value, ConditionMode.GREATER_THEN, "category", "java.lang.String", "String");
          return this;
      }

      public HnwJgpzCriteria andCategoryGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("category", value, ConditionMode.GREATER_EQUAL, "category", "java.lang.String", "String");
          return this;
      }

      public HnwJgpzCriteria andCategoryLessThan(java.lang.String value) {
          addCriterion("category", value, ConditionMode.LESS_THEN, "category", "java.lang.String", "String");
          return this;
      }

      public HnwJgpzCriteria andCategoryLessThanOrEqualTo(java.lang.String value) {
          addCriterion("category", value, ConditionMode.LESS_EQUAL, "category", "java.lang.String", "String");
          return this;
      }

      public HnwJgpzCriteria andCategoryBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("category", value1, value2, ConditionMode.BETWEEN, "category", "java.lang.String", "String");
    	  return this;
      }

      public HnwJgpzCriteria andCategoryNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("category", value1, value2, ConditionMode.NOT_BETWEEN, "category", "java.lang.String", "String");
          return this;
      }
        
      public HnwJgpzCriteria andCategoryIn(List<java.lang.String> values) {
          addCriterion("category", values, ConditionMode.IN, "category", "java.lang.String", "String");
          return this;
      }

      public HnwJgpzCriteria andCategoryNotIn(List<java.lang.String> values) {
          addCriterion("category", values, ConditionMode.NOT_IN, "category", "java.lang.String", "String");
          return this;
      }
	public HnwJgpzCriteria andTagIsNull() {
		isnull("tag");
		return this;
	}
	
	public HnwJgpzCriteria andTagIsNotNull() {
		notNull("tag");
		return this;
	}
	
	public HnwJgpzCriteria andTagIsEmpty() {
		empty("tag");
		return this;
	}

	public HnwJgpzCriteria andTagIsNotEmpty() {
		notEmpty("tag");
		return this;
	}
        public HnwJgpzCriteria andTagLike(java.lang.String value) {
    	   addCriterion("tag", value, ConditionMode.FUZZY, "tag", "java.lang.String", "String");
    	   return this;
      }

      public HnwJgpzCriteria andTagNotLike(java.lang.String value) {
          addCriterion("tag", value, ConditionMode.NOT_FUZZY, "tag", "java.lang.String", "String");
          return this;
      }
      public HnwJgpzCriteria andTagEqualTo(java.lang.String value) {
          addCriterion("tag", value, ConditionMode.EQUAL, "tag", "java.lang.String", "String");
          return this;
      }

      public HnwJgpzCriteria andTagNotEqualTo(java.lang.String value) {
          addCriterion("tag", value, ConditionMode.NOT_EQUAL, "tag", "java.lang.String", "String");
          return this;
      }

      public HnwJgpzCriteria andTagGreaterThan(java.lang.String value) {
          addCriterion("tag", value, ConditionMode.GREATER_THEN, "tag", "java.lang.String", "String");
          return this;
      }

      public HnwJgpzCriteria andTagGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("tag", value, ConditionMode.GREATER_EQUAL, "tag", "java.lang.String", "String");
          return this;
      }

      public HnwJgpzCriteria andTagLessThan(java.lang.String value) {
          addCriterion("tag", value, ConditionMode.LESS_THEN, "tag", "java.lang.String", "String");
          return this;
      }

      public HnwJgpzCriteria andTagLessThanOrEqualTo(java.lang.String value) {
          addCriterion("tag", value, ConditionMode.LESS_EQUAL, "tag", "java.lang.String", "String");
          return this;
      }

      public HnwJgpzCriteria andTagBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("tag", value1, value2, ConditionMode.BETWEEN, "tag", "java.lang.String", "String");
    	  return this;
      }

      public HnwJgpzCriteria andTagNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("tag", value1, value2, ConditionMode.NOT_BETWEEN, "tag", "java.lang.String", "String");
          return this;
      }
        
      public HnwJgpzCriteria andTagIn(List<java.lang.String> values) {
          addCriterion("tag", values, ConditionMode.IN, "tag", "java.lang.String", "String");
          return this;
      }

      public HnwJgpzCriteria andTagNotIn(List<java.lang.String> values) {
          addCriterion("tag", values, ConditionMode.NOT_IN, "tag", "java.lang.String", "String");
          return this;
      }
	public HnwJgpzCriteria andProductIsNull() {
		isnull("product");
		return this;
	}
	
	public HnwJgpzCriteria andProductIsNotNull() {
		notNull("product");
		return this;
	}
	
	public HnwJgpzCriteria andProductIsEmpty() {
		empty("product");
		return this;
	}

	public HnwJgpzCriteria andProductIsNotEmpty() {
		notEmpty("product");
		return this;
	}
        public HnwJgpzCriteria andProductLike(java.lang.String value) {
    	   addCriterion("product", value, ConditionMode.FUZZY, "product", "java.lang.String", "String");
    	   return this;
      }

      public HnwJgpzCriteria andProductNotLike(java.lang.String value) {
          addCriterion("product", value, ConditionMode.NOT_FUZZY, "product", "java.lang.String", "String");
          return this;
      }
      public HnwJgpzCriteria andProductEqualTo(java.lang.String value) {
          addCriterion("product", value, ConditionMode.EQUAL, "product", "java.lang.String", "String");
          return this;
      }

      public HnwJgpzCriteria andProductNotEqualTo(java.lang.String value) {
          addCriterion("product", value, ConditionMode.NOT_EQUAL, "product", "java.lang.String", "String");
          return this;
      }

      public HnwJgpzCriteria andProductGreaterThan(java.lang.String value) {
          addCriterion("product", value, ConditionMode.GREATER_THEN, "product", "java.lang.String", "String");
          return this;
      }

      public HnwJgpzCriteria andProductGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("product", value, ConditionMode.GREATER_EQUAL, "product", "java.lang.String", "String");
          return this;
      }

      public HnwJgpzCriteria andProductLessThan(java.lang.String value) {
          addCriterion("product", value, ConditionMode.LESS_THEN, "product", "java.lang.String", "String");
          return this;
      }

      public HnwJgpzCriteria andProductLessThanOrEqualTo(java.lang.String value) {
          addCriterion("product", value, ConditionMode.LESS_EQUAL, "product", "java.lang.String", "String");
          return this;
      }

      public HnwJgpzCriteria andProductBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("product", value1, value2, ConditionMode.BETWEEN, "product", "java.lang.String", "String");
    	  return this;
      }

      public HnwJgpzCriteria andProductNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("product", value1, value2, ConditionMode.NOT_BETWEEN, "product", "java.lang.String", "String");
          return this;
      }
        
      public HnwJgpzCriteria andProductIn(List<java.lang.String> values) {
          addCriterion("product", values, ConditionMode.IN, "product", "java.lang.String", "String");
          return this;
      }

      public HnwJgpzCriteria andProductNotIn(List<java.lang.String> values) {
          addCriterion("product", values, ConditionMode.NOT_IN, "product", "java.lang.String", "String");
          return this;
      }
	public HnwJgpzCriteria andPlaceIsNull() {
		isnull("place");
		return this;
	}
	
	public HnwJgpzCriteria andPlaceIsNotNull() {
		notNull("place");
		return this;
	}
	
	public HnwJgpzCriteria andPlaceIsEmpty() {
		empty("place");
		return this;
	}

	public HnwJgpzCriteria andPlaceIsNotEmpty() {
		notEmpty("place");
		return this;
	}
        public HnwJgpzCriteria andPlaceLike(java.lang.String value) {
    	   addCriterion("place", value, ConditionMode.FUZZY, "place", "java.lang.String", "String");
    	   return this;
      }

      public HnwJgpzCriteria andPlaceNotLike(java.lang.String value) {
          addCriterion("place", value, ConditionMode.NOT_FUZZY, "place", "java.lang.String", "String");
          return this;
      }
      public HnwJgpzCriteria andPlaceEqualTo(java.lang.String value) {
          addCriterion("place", value, ConditionMode.EQUAL, "place", "java.lang.String", "String");
          return this;
      }

      public HnwJgpzCriteria andPlaceNotEqualTo(java.lang.String value) {
          addCriterion("place", value, ConditionMode.NOT_EQUAL, "place", "java.lang.String", "String");
          return this;
      }

      public HnwJgpzCriteria andPlaceGreaterThan(java.lang.String value) {
          addCriterion("place", value, ConditionMode.GREATER_THEN, "place", "java.lang.String", "String");
          return this;
      }

      public HnwJgpzCriteria andPlaceGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("place", value, ConditionMode.GREATER_EQUAL, "place", "java.lang.String", "String");
          return this;
      }

      public HnwJgpzCriteria andPlaceLessThan(java.lang.String value) {
          addCriterion("place", value, ConditionMode.LESS_THEN, "place", "java.lang.String", "String");
          return this;
      }

      public HnwJgpzCriteria andPlaceLessThanOrEqualTo(java.lang.String value) {
          addCriterion("place", value, ConditionMode.LESS_EQUAL, "place", "java.lang.String", "String");
          return this;
      }

      public HnwJgpzCriteria andPlaceBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("place", value1, value2, ConditionMode.BETWEEN, "place", "java.lang.String", "String");
    	  return this;
      }

      public HnwJgpzCriteria andPlaceNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("place", value1, value2, ConditionMode.NOT_BETWEEN, "place", "java.lang.String", "String");
          return this;
      }
        
      public HnwJgpzCriteria andPlaceIn(List<java.lang.String> values) {
          addCriterion("place", values, ConditionMode.IN, "place", "java.lang.String", "String");
          return this;
      }

      public HnwJgpzCriteria andPlaceNotIn(List<java.lang.String> values) {
          addCriterion("place", values, ConditionMode.NOT_IN, "place", "java.lang.String", "String");
          return this;
      }
	public HnwJgpzCriteria andPriceIsNull() {
		isnull("price");
		return this;
	}
	
	public HnwJgpzCriteria andPriceIsNotNull() {
		notNull("price");
		return this;
	}
	
	public HnwJgpzCriteria andPriceIsEmpty() {
		empty("price");
		return this;
	}

	public HnwJgpzCriteria andPriceIsNotEmpty() {
		notEmpty("price");
		return this;
	}
        public HnwJgpzCriteria andPriceLike(java.lang.String value) {
    	   addCriterion("price", value, ConditionMode.FUZZY, "price", "java.lang.String", "String");
    	   return this;
      }

      public HnwJgpzCriteria andPriceNotLike(java.lang.String value) {
          addCriterion("price", value, ConditionMode.NOT_FUZZY, "price", "java.lang.String", "String");
          return this;
      }
      public HnwJgpzCriteria andPriceEqualTo(java.lang.String value) {
          addCriterion("price", value, ConditionMode.EQUAL, "price", "java.lang.String", "String");
          return this;
      }

      public HnwJgpzCriteria andPriceNotEqualTo(java.lang.String value) {
          addCriterion("price", value, ConditionMode.NOT_EQUAL, "price", "java.lang.String", "String");
          return this;
      }

      public HnwJgpzCriteria andPriceGreaterThan(java.lang.String value) {
          addCriterion("price", value, ConditionMode.GREATER_THEN, "price", "java.lang.String", "String");
          return this;
      }

      public HnwJgpzCriteria andPriceGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("price", value, ConditionMode.GREATER_EQUAL, "price", "java.lang.String", "String");
          return this;
      }

      public HnwJgpzCriteria andPriceLessThan(java.lang.String value) {
          addCriterion("price", value, ConditionMode.LESS_THEN, "price", "java.lang.String", "String");
          return this;
      }

      public HnwJgpzCriteria andPriceLessThanOrEqualTo(java.lang.String value) {
          addCriterion("price", value, ConditionMode.LESS_EQUAL, "price", "java.lang.String", "String");
          return this;
      }

      public HnwJgpzCriteria andPriceBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("price", value1, value2, ConditionMode.BETWEEN, "price", "java.lang.String", "String");
    	  return this;
      }

      public HnwJgpzCriteria andPriceNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("price", value1, value2, ConditionMode.NOT_BETWEEN, "price", "java.lang.String", "String");
          return this;
      }
        
      public HnwJgpzCriteria andPriceIn(List<java.lang.String> values) {
          addCriterion("price", values, ConditionMode.IN, "price", "java.lang.String", "String");
          return this;
      }

      public HnwJgpzCriteria andPriceNotIn(List<java.lang.String> values) {
          addCriterion("price", values, ConditionMode.NOT_IN, "price", "java.lang.String", "String");
          return this;
      }
	public HnwJgpzCriteria andStatusIsNull() {
		isnull("status");
		return this;
	}
	
	public HnwJgpzCriteria andStatusIsNotNull() {
		notNull("status");
		return this;
	}
	
	public HnwJgpzCriteria andStatusIsEmpty() {
		empty("status");
		return this;
	}

	public HnwJgpzCriteria andStatusIsNotEmpty() {
		notEmpty("status");
		return this;
	}
        public HnwJgpzCriteria andStatusLike(java.lang.String value) {
    	   addCriterion("status", value, ConditionMode.FUZZY, "status", "java.lang.String", "String");
    	   return this;
      }

      public HnwJgpzCriteria andStatusNotLike(java.lang.String value) {
          addCriterion("status", value, ConditionMode.NOT_FUZZY, "status", "java.lang.String", "String");
          return this;
      }
      public HnwJgpzCriteria andStatusEqualTo(java.lang.String value) {
          addCriterion("status", value, ConditionMode.EQUAL, "status", "java.lang.String", "String");
          return this;
      }

      public HnwJgpzCriteria andStatusNotEqualTo(java.lang.String value) {
          addCriterion("status", value, ConditionMode.NOT_EQUAL, "status", "java.lang.String", "String");
          return this;
      }

      public HnwJgpzCriteria andStatusGreaterThan(java.lang.String value) {
          addCriterion("status", value, ConditionMode.GREATER_THEN, "status", "java.lang.String", "String");
          return this;
      }

      public HnwJgpzCriteria andStatusGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("status", value, ConditionMode.GREATER_EQUAL, "status", "java.lang.String", "String");
          return this;
      }

      public HnwJgpzCriteria andStatusLessThan(java.lang.String value) {
          addCriterion("status", value, ConditionMode.LESS_THEN, "status", "java.lang.String", "String");
          return this;
      }

      public HnwJgpzCriteria andStatusLessThanOrEqualTo(java.lang.String value) {
          addCriterion("status", value, ConditionMode.LESS_EQUAL, "status", "java.lang.String", "String");
          return this;
      }

      public HnwJgpzCriteria andStatusBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("status", value1, value2, ConditionMode.BETWEEN, "status", "java.lang.String", "String");
    	  return this;
      }

      public HnwJgpzCriteria andStatusNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("status", value1, value2, ConditionMode.NOT_BETWEEN, "status", "java.lang.String", "String");
          return this;
      }
        
      public HnwJgpzCriteria andStatusIn(List<java.lang.String> values) {
          addCriterion("status", values, ConditionMode.IN, "status", "java.lang.String", "String");
          return this;
      }

      public HnwJgpzCriteria andStatusNotIn(List<java.lang.String> values) {
          addCriterion("status", values, ConditionMode.NOT_IN, "status", "java.lang.String", "String");
          return this;
      }
	public HnwJgpzCriteria andUpTimeIsNull() {
		isnull("up_time");
		return this;
	}
	
	public HnwJgpzCriteria andUpTimeIsNotNull() {
		notNull("up_time");
		return this;
	}
	
	public HnwJgpzCriteria andUpTimeIsEmpty() {
		empty("up_time");
		return this;
	}

	public HnwJgpzCriteria andUpTimeIsNotEmpty() {
		notEmpty("up_time");
		return this;
	}
       public HnwJgpzCriteria andUpTimeEqualTo(java.util.Date value) {
          addCriterion("up_time", value, ConditionMode.EQUAL, "upTime", "java.util.Date", "String");
          return this;
      }

      public HnwJgpzCriteria andUpTimeNotEqualTo(java.util.Date value) {
          addCriterion("up_time", value, ConditionMode.NOT_EQUAL, "upTime", "java.util.Date", "String");
          return this;
      }

      public HnwJgpzCriteria andUpTimeGreaterThan(java.util.Date value) {
          addCriterion("up_time", value, ConditionMode.GREATER_THEN, "upTime", "java.util.Date", "String");
          return this;
      }

      public HnwJgpzCriteria andUpTimeGreaterThanOrEqualTo(java.util.Date value) {
          addCriterion("up_time", value, ConditionMode.GREATER_EQUAL, "upTime", "java.util.Date", "String");
          return this;
      }

      public HnwJgpzCriteria andUpTimeLessThan(java.util.Date value) {
          addCriterion("up_time", value, ConditionMode.LESS_THEN, "upTime", "java.util.Date", "String");
          return this;
      }

      public HnwJgpzCriteria andUpTimeLessThanOrEqualTo(java.util.Date value) {
          addCriterion("up_time", value, ConditionMode.LESS_EQUAL, "upTime", "java.util.Date", "String");
          return this;
      }

      public HnwJgpzCriteria andUpTimeBetween(java.util.Date value1, java.util.Date value2) {
    	  addCriterion("up_time", value1, value2, ConditionMode.BETWEEN, "upTime", "java.util.Date", "String");
    	  return this;
      }

      public HnwJgpzCriteria andUpTimeNotBetween(java.util.Date value1, java.util.Date value2) {
          addCriterion("up_time", value1, value2, ConditionMode.NOT_BETWEEN, "upTime", "java.util.Date", "String");
          return this;
      }
        
      public HnwJgpzCriteria andUpTimeIn(List<java.util.Date> values) {
          addCriterion("up_time", values, ConditionMode.IN, "upTime", "java.util.Date", "String");
          return this;
      }

      public HnwJgpzCriteria andUpTimeNotIn(List<java.util.Date> values) {
          addCriterion("up_time", values, ConditionMode.NOT_IN, "upTime", "java.util.Date", "String");
          return this;
      }
	public HnwJgpzCriteria andUrlIsNull() {
		isnull("url");
		return this;
	}
	
	public HnwJgpzCriteria andUrlIsNotNull() {
		notNull("url");
		return this;
	}
	
	public HnwJgpzCriteria andUrlIsEmpty() {
		empty("url");
		return this;
	}

	public HnwJgpzCriteria andUrlIsNotEmpty() {
		notEmpty("url");
		return this;
	}
        public HnwJgpzCriteria andUrlLike(java.lang.String value) {
    	   addCriterion("url", value, ConditionMode.FUZZY, "url", "java.lang.String", "String");
    	   return this;
      }

      public HnwJgpzCriteria andUrlNotLike(java.lang.String value) {
          addCriterion("url", value, ConditionMode.NOT_FUZZY, "url", "java.lang.String", "String");
          return this;
      }
      public HnwJgpzCriteria andUrlEqualTo(java.lang.String value) {
          addCriterion("url", value, ConditionMode.EQUAL, "url", "java.lang.String", "String");
          return this;
      }

      public HnwJgpzCriteria andUrlNotEqualTo(java.lang.String value) {
          addCriterion("url", value, ConditionMode.NOT_EQUAL, "url", "java.lang.String", "String");
          return this;
      }

      public HnwJgpzCriteria andUrlGreaterThan(java.lang.String value) {
          addCriterion("url", value, ConditionMode.GREATER_THEN, "url", "java.lang.String", "String");
          return this;
      }

      public HnwJgpzCriteria andUrlGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("url", value, ConditionMode.GREATER_EQUAL, "url", "java.lang.String", "String");
          return this;
      }

      public HnwJgpzCriteria andUrlLessThan(java.lang.String value) {
          addCriterion("url", value, ConditionMode.LESS_THEN, "url", "java.lang.String", "String");
          return this;
      }

      public HnwJgpzCriteria andUrlLessThanOrEqualTo(java.lang.String value) {
          addCriterion("url", value, ConditionMode.LESS_EQUAL, "url", "java.lang.String", "String");
          return this;
      }

      public HnwJgpzCriteria andUrlBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("url", value1, value2, ConditionMode.BETWEEN, "url", "java.lang.String", "String");
    	  return this;
      }

      public HnwJgpzCriteria andUrlNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("url", value1, value2, ConditionMode.NOT_BETWEEN, "url", "java.lang.String", "String");
          return this;
      }
        
      public HnwJgpzCriteria andUrlIn(List<java.lang.String> values) {
          addCriterion("url", values, ConditionMode.IN, "url", "java.lang.String", "String");
          return this;
      }

      public HnwJgpzCriteria andUrlNotIn(List<java.lang.String> values) {
          addCriterion("url", values, ConditionMode.NOT_IN, "url", "java.lang.String", "String");
          return this;
      }
	public HnwJgpzCriteria andIdIsNull() {
		isnull("id");
		return this;
	}
	
	public HnwJgpzCriteria andIdIsNotNull() {
		notNull("id");
		return this;
	}
	
	public HnwJgpzCriteria andIdIsEmpty() {
		empty("id");
		return this;
	}

	public HnwJgpzCriteria andIdIsNotEmpty() {
		notEmpty("id");
		return this;
	}
       public HnwJgpzCriteria andIdEqualTo(java.lang.Long value) {
          addCriterion("id", value, ConditionMode.EQUAL, "id", "java.lang.Long", "Float");
          return this;
      }

      public HnwJgpzCriteria andIdNotEqualTo(java.lang.Long value) {
          addCriterion("id", value, ConditionMode.NOT_EQUAL, "id", "java.lang.Long", "Float");
          return this;
      }

      public HnwJgpzCriteria andIdGreaterThan(java.lang.Long value) {
          addCriterion("id", value, ConditionMode.GREATER_THEN, "id", "java.lang.Long", "Float");
          return this;
      }

      public HnwJgpzCriteria andIdGreaterThanOrEqualTo(java.lang.Long value) {
          addCriterion("id", value, ConditionMode.GREATER_EQUAL, "id", "java.lang.Long", "Float");
          return this;
      }

      public HnwJgpzCriteria andIdLessThan(java.lang.Long value) {
          addCriterion("id", value, ConditionMode.LESS_THEN, "id", "java.lang.Long", "Float");
          return this;
      }

      public HnwJgpzCriteria andIdLessThanOrEqualTo(java.lang.Long value) {
          addCriterion("id", value, ConditionMode.LESS_EQUAL, "id", "java.lang.Long", "Float");
          return this;
      }

      public HnwJgpzCriteria andIdBetween(java.lang.Long value1, java.lang.Long value2) {
    	  addCriterion("id", value1, value2, ConditionMode.BETWEEN, "id", "java.lang.Long", "Float");
    	  return this;
      }

      public HnwJgpzCriteria andIdNotBetween(java.lang.Long value1, java.lang.Long value2) {
          addCriterion("id", value1, value2, ConditionMode.NOT_BETWEEN, "id", "java.lang.Long", "Float");
          return this;
      }
        
      public HnwJgpzCriteria andIdIn(List<java.lang.Long> values) {
          addCriterion("id", values, ConditionMode.IN, "id", "java.lang.Long", "Float");
          return this;
      }

      public HnwJgpzCriteria andIdNotIn(List<java.lang.Long> values) {
          addCriterion("id", values, ConditionMode.NOT_IN, "id", "java.lang.Long", "Float");
          return this;
      }
}