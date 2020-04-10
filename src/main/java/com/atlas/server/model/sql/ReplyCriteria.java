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
 * @date 2020-04-10
 * @version 1.0
 * @since 1.0
 */
public class ReplyCriteria extends Columns implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static ReplyCriteria create() {
		return new ReplyCriteria();
	}
	
	public static ReplyCriteria create(Column column) {
		ReplyCriteria that = new ReplyCriteria();
		that.add(column);
        return that;
    }

    public static ReplyCriteria create(String name, Object value) {
        return (ReplyCriteria) create().eq(name, value);
    }
    
    public Example example() {
    	return Example.create("at_reply", this);
    }
    
    /**
     * equals
     *
     * @param name
     * @param value
     * @return
     */
    public ReplyCriteria eq(String name, Object value) {
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
    public ReplyCriteria ne(String name, Object value) {
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

    public ReplyCriteria like(String name, Object value) {
    	super.like(name, value);
        return this;
    }
    
    public ReplyCriteria notLike(String name, Object value) {
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
    public ReplyCriteria gt(String name, Object value) {
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
    public ReplyCriteria ge(String name, Object value) {
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
    public ReplyCriteria lt(String name, Object value) {
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
    public ReplyCriteria le(String name, Object value) {
    	super.le(name, value);
        return this;
    }
    
    public ReplyCriteria isnull(String name) {
    	super.isnull(name);
        return this;
    } 

    public ReplyCriteria notNull(String name) {
    	super.notNull(name);
        return this;
    } 
    
    public ReplyCriteria empty(String name) {
    	super.empty(name);
        return this;
    } 
    
    public ReplyCriteria notEmpty(String name) {
    	super.notEmpty(name);
        return this;
    } 
    
    public ReplyCriteria add(Column column) {
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
		 
	public ReplyCriteria andIdIsNull() {
		isnull("id");
		return this;
	}
	
	public ReplyCriteria andIdIsNotNull() {
		notNull("id");
		return this;
	}
	
	public ReplyCriteria andIdIsEmpty() {
		empty("id");
		return this;
	}

	public ReplyCriteria andIdIsNotEmpty() {
		notEmpty("id");
		return this;
	}
       public ReplyCriteria andIdEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andIdNotEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.NOT_EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andIdGreaterThan(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.GREATER_THEN, "id", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andIdGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.GREATER_EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andIdLessThan(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.LESS_THEN, "id", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andIdLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("id", value, ConditionMode.LESS_EQUAL, "id", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andIdBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("id", value1, value2, ConditionMode.BETWEEN, "id", "java.lang.Integer", "Float");
    	  return this;
      }

      public ReplyCriteria andIdNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("id", value1, value2, ConditionMode.NOT_BETWEEN, "id", "java.lang.Integer", "Float");
          return this;
      }
        
      public ReplyCriteria andIdIn(List<java.lang.Integer> values) {
          addCriterion("id", values, ConditionMode.IN, "id", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andIdNotIn(List<java.lang.Integer> values) {
          addCriterion("id", values, ConditionMode.NOT_IN, "id", "java.lang.Integer", "Float");
          return this;
      }
	public ReplyCriteria andTorridIsNull() {
		isnull("torrid");
		return this;
	}
	
	public ReplyCriteria andTorridIsNotNull() {
		notNull("torrid");
		return this;
	}
	
	public ReplyCriteria andTorridIsEmpty() {
		empty("torrid");
		return this;
	}

	public ReplyCriteria andTorridIsNotEmpty() {
		notEmpty("torrid");
		return this;
	}
       public ReplyCriteria andTorridEqualTo(java.lang.Integer value) {
          addCriterion("torrid", value, ConditionMode.EQUAL, "torrid", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andTorridNotEqualTo(java.lang.Integer value) {
          addCriterion("torrid", value, ConditionMode.NOT_EQUAL, "torrid", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andTorridGreaterThan(java.lang.Integer value) {
          addCriterion("torrid", value, ConditionMode.GREATER_THEN, "torrid", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andTorridGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("torrid", value, ConditionMode.GREATER_EQUAL, "torrid", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andTorridLessThan(java.lang.Integer value) {
          addCriterion("torrid", value, ConditionMode.LESS_THEN, "torrid", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andTorridLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("torrid", value, ConditionMode.LESS_EQUAL, "torrid", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andTorridBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("torrid", value1, value2, ConditionMode.BETWEEN, "torrid", "java.lang.Integer", "Float");
    	  return this;
      }

      public ReplyCriteria andTorridNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("torrid", value1, value2, ConditionMode.NOT_BETWEEN, "torrid", "java.lang.Integer", "Float");
          return this;
      }
        
      public ReplyCriteria andTorridIn(List<java.lang.Integer> values) {
          addCriterion("torrid", values, ConditionMode.IN, "torrid", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andTorridNotIn(List<java.lang.Integer> values) {
          addCriterion("torrid", values, ConditionMode.NOT_IN, "torrid", "java.lang.Integer", "Float");
          return this;
      }
	public ReplyCriteria andTorrIsNull() {
		isnull("torr");
		return this;
	}
	
	public ReplyCriteria andTorrIsNotNull() {
		notNull("torr");
		return this;
	}
	
	public ReplyCriteria andTorrIsEmpty() {
		empty("torr");
		return this;
	}

	public ReplyCriteria andTorrIsNotEmpty() {
		notEmpty("torr");
		return this;
	}
       public ReplyCriteria andTorrEqualTo(java.lang.Integer value) {
          addCriterion("torr", value, ConditionMode.EQUAL, "torr", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andTorrNotEqualTo(java.lang.Integer value) {
          addCriterion("torr", value, ConditionMode.NOT_EQUAL, "torr", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andTorrGreaterThan(java.lang.Integer value) {
          addCriterion("torr", value, ConditionMode.GREATER_THEN, "torr", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andTorrGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("torr", value, ConditionMode.GREATER_EQUAL, "torr", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andTorrLessThan(java.lang.Integer value) {
          addCriterion("torr", value, ConditionMode.LESS_THEN, "torr", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andTorrLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("torr", value, ConditionMode.LESS_EQUAL, "torr", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andTorrBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("torr", value1, value2, ConditionMode.BETWEEN, "torr", "java.lang.Integer", "Float");
    	  return this;
      }

      public ReplyCriteria andTorrNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("torr", value1, value2, ConditionMode.NOT_BETWEEN, "torr", "java.lang.Integer", "Float");
          return this;
      }
        
      public ReplyCriteria andTorrIn(List<java.lang.Integer> values) {
          addCriterion("torr", values, ConditionMode.IN, "torr", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andTorrNotIn(List<java.lang.Integer> values) {
          addCriterion("torr", values, ConditionMode.NOT_IN, "torr", "java.lang.Integer", "Float");
          return this;
      }
	public ReplyCriteria andUidIsNull() {
		isnull("uid");
		return this;
	}
	
	public ReplyCriteria andUidIsNotNull() {
		notNull("uid");
		return this;
	}
	
	public ReplyCriteria andUidIsEmpty() {
		empty("uid");
		return this;
	}

	public ReplyCriteria andUidIsNotEmpty() {
		notEmpty("uid");
		return this;
	}
       public ReplyCriteria andUidEqualTo(java.lang.Integer value) {
          addCriterion("uid", value, ConditionMode.EQUAL, "uid", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andUidNotEqualTo(java.lang.Integer value) {
          addCriterion("uid", value, ConditionMode.NOT_EQUAL, "uid", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andUidGreaterThan(java.lang.Integer value) {
          addCriterion("uid", value, ConditionMode.GREATER_THEN, "uid", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andUidGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("uid", value, ConditionMode.GREATER_EQUAL, "uid", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andUidLessThan(java.lang.Integer value) {
          addCriterion("uid", value, ConditionMode.LESS_THEN, "uid", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andUidLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("uid", value, ConditionMode.LESS_EQUAL, "uid", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andUidBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("uid", value1, value2, ConditionMode.BETWEEN, "uid", "java.lang.Integer", "Float");
    	  return this;
      }

      public ReplyCriteria andUidNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("uid", value1, value2, ConditionMode.NOT_BETWEEN, "uid", "java.lang.Integer", "Float");
          return this;
      }
        
      public ReplyCriteria andUidIn(List<java.lang.Integer> values) {
          addCriterion("uid", values, ConditionMode.IN, "uid", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andUidNotIn(List<java.lang.Integer> values) {
          addCriterion("uid", values, ConditionMode.NOT_IN, "uid", "java.lang.Integer", "Float");
          return this;
      }
	public ReplyCriteria andTouidIsNull() {
		isnull("touid");
		return this;
	}
	
	public ReplyCriteria andTouidIsNotNull() {
		notNull("touid");
		return this;
	}
	
	public ReplyCriteria andTouidIsEmpty() {
		empty("touid");
		return this;
	}

	public ReplyCriteria andTouidIsNotEmpty() {
		notEmpty("touid");
		return this;
	}
       public ReplyCriteria andTouidEqualTo(java.lang.Integer value) {
          addCriterion("touid", value, ConditionMode.EQUAL, "touid", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andTouidNotEqualTo(java.lang.Integer value) {
          addCriterion("touid", value, ConditionMode.NOT_EQUAL, "touid", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andTouidGreaterThan(java.lang.Integer value) {
          addCriterion("touid", value, ConditionMode.GREATER_THEN, "touid", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andTouidGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("touid", value, ConditionMode.GREATER_EQUAL, "touid", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andTouidLessThan(java.lang.Integer value) {
          addCriterion("touid", value, ConditionMode.LESS_THEN, "touid", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andTouidLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("touid", value, ConditionMode.LESS_EQUAL, "touid", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andTouidBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("touid", value1, value2, ConditionMode.BETWEEN, "touid", "java.lang.Integer", "Float");
    	  return this;
      }

      public ReplyCriteria andTouidNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("touid", value1, value2, ConditionMode.NOT_BETWEEN, "touid", "java.lang.Integer", "Float");
          return this;
      }
        
      public ReplyCriteria andTouidIn(List<java.lang.Integer> values) {
          addCriterion("touid", values, ConditionMode.IN, "touid", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andTouidNotIn(List<java.lang.Integer> values) {
          addCriterion("touid", values, ConditionMode.NOT_IN, "touid", "java.lang.Integer", "Float");
          return this;
      }
	public ReplyCriteria andUnameIsNull() {
		isnull("uname");
		return this;
	}
	
	public ReplyCriteria andUnameIsNotNull() {
		notNull("uname");
		return this;
	}
	
	public ReplyCriteria andUnameIsEmpty() {
		empty("uname");
		return this;
	}

	public ReplyCriteria andUnameIsNotEmpty() {
		notEmpty("uname");
		return this;
	}
        public ReplyCriteria andUnameLike(java.lang.String value) {
    	   addCriterion("uname", value, ConditionMode.FUZZY, "uname", "java.lang.String", "Float");
    	   return this;
      }

      public ReplyCriteria andUnameNotLike(java.lang.String value) {
          addCriterion("uname", value, ConditionMode.NOT_FUZZY, "uname", "java.lang.String", "Float");
          return this;
      }
      public ReplyCriteria andUnameEqualTo(java.lang.String value) {
          addCriterion("uname", value, ConditionMode.EQUAL, "uname", "java.lang.String", "String");
          return this;
      }

      public ReplyCriteria andUnameNotEqualTo(java.lang.String value) {
          addCriterion("uname", value, ConditionMode.NOT_EQUAL, "uname", "java.lang.String", "String");
          return this;
      }

      public ReplyCriteria andUnameGreaterThan(java.lang.String value) {
          addCriterion("uname", value, ConditionMode.GREATER_THEN, "uname", "java.lang.String", "String");
          return this;
      }

      public ReplyCriteria andUnameGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("uname", value, ConditionMode.GREATER_EQUAL, "uname", "java.lang.String", "String");
          return this;
      }

      public ReplyCriteria andUnameLessThan(java.lang.String value) {
          addCriterion("uname", value, ConditionMode.LESS_THEN, "uname", "java.lang.String", "String");
          return this;
      }

      public ReplyCriteria andUnameLessThanOrEqualTo(java.lang.String value) {
          addCriterion("uname", value, ConditionMode.LESS_EQUAL, "uname", "java.lang.String", "String");
          return this;
      }

      public ReplyCriteria andUnameBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("uname", value1, value2, ConditionMode.BETWEEN, "uname", "java.lang.String", "String");
    	  return this;
      }

      public ReplyCriteria andUnameNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("uname", value1, value2, ConditionMode.NOT_BETWEEN, "uname", "java.lang.String", "String");
          return this;
      }
        
      public ReplyCriteria andUnameIn(List<java.lang.String> values) {
          addCriterion("uname", values, ConditionMode.IN, "uname", "java.lang.String", "String");
          return this;
      }

      public ReplyCriteria andUnameNotIn(List<java.lang.String> values) {
          addCriterion("uname", values, ConditionMode.NOT_IN, "uname", "java.lang.String", "String");
          return this;
      }
	public ReplyCriteria andTounameIsNull() {
		isnull("touname");
		return this;
	}
	
	public ReplyCriteria andTounameIsNotNull() {
		notNull("touname");
		return this;
	}
	
	public ReplyCriteria andTounameIsEmpty() {
		empty("touname");
		return this;
	}

	public ReplyCriteria andTounameIsNotEmpty() {
		notEmpty("touname");
		return this;
	}
        public ReplyCriteria andTounameLike(java.lang.String value) {
    	   addCriterion("touname", value, ConditionMode.FUZZY, "touname", "java.lang.String", "String");
    	   return this;
      }

      public ReplyCriteria andTounameNotLike(java.lang.String value) {
          addCriterion("touname", value, ConditionMode.NOT_FUZZY, "touname", "java.lang.String", "String");
          return this;
      }
      public ReplyCriteria andTounameEqualTo(java.lang.String value) {
          addCriterion("touname", value, ConditionMode.EQUAL, "touname", "java.lang.String", "String");
          return this;
      }

      public ReplyCriteria andTounameNotEqualTo(java.lang.String value) {
          addCriterion("touname", value, ConditionMode.NOT_EQUAL, "touname", "java.lang.String", "String");
          return this;
      }

      public ReplyCriteria andTounameGreaterThan(java.lang.String value) {
          addCriterion("touname", value, ConditionMode.GREATER_THEN, "touname", "java.lang.String", "String");
          return this;
      }

      public ReplyCriteria andTounameGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("touname", value, ConditionMode.GREATER_EQUAL, "touname", "java.lang.String", "String");
          return this;
      }

      public ReplyCriteria andTounameLessThan(java.lang.String value) {
          addCriterion("touname", value, ConditionMode.LESS_THEN, "touname", "java.lang.String", "String");
          return this;
      }

      public ReplyCriteria andTounameLessThanOrEqualTo(java.lang.String value) {
          addCriterion("touname", value, ConditionMode.LESS_EQUAL, "touname", "java.lang.String", "String");
          return this;
      }

      public ReplyCriteria andTounameBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("touname", value1, value2, ConditionMode.BETWEEN, "touname", "java.lang.String", "String");
    	  return this;
      }

      public ReplyCriteria andTounameNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("touname", value1, value2, ConditionMode.NOT_BETWEEN, "touname", "java.lang.String", "String");
          return this;
      }
        
      public ReplyCriteria andTounameIn(List<java.lang.String> values) {
          addCriterion("touname", values, ConditionMode.IN, "touname", "java.lang.String", "String");
          return this;
      }

      public ReplyCriteria andTounameNotIn(List<java.lang.String> values) {
          addCriterion("touname", values, ConditionMode.NOT_IN, "touname", "java.lang.String", "String");
          return this;
      }
	public ReplyCriteria andUheadurlIsNull() {
		isnull("uheadurl");
		return this;
	}
	
	public ReplyCriteria andUheadurlIsNotNull() {
		notNull("uheadurl");
		return this;
	}
	
	public ReplyCriteria andUheadurlIsEmpty() {
		empty("uheadurl");
		return this;
	}

	public ReplyCriteria andUheadurlIsNotEmpty() {
		notEmpty("uheadurl");
		return this;
	}
        public ReplyCriteria andUheadurlLike(java.lang.String value) {
    	   addCriterion("uheadurl", value, ConditionMode.FUZZY, "uheadurl", "java.lang.String", "String");
    	   return this;
      }

      public ReplyCriteria andUheadurlNotLike(java.lang.String value) {
          addCriterion("uheadurl", value, ConditionMode.NOT_FUZZY, "uheadurl", "java.lang.String", "String");
          return this;
      }
      public ReplyCriteria andUheadurlEqualTo(java.lang.String value) {
          addCriterion("uheadurl", value, ConditionMode.EQUAL, "uheadurl", "java.lang.String", "String");
          return this;
      }

      public ReplyCriteria andUheadurlNotEqualTo(java.lang.String value) {
          addCriterion("uheadurl", value, ConditionMode.NOT_EQUAL, "uheadurl", "java.lang.String", "String");
          return this;
      }

      public ReplyCriteria andUheadurlGreaterThan(java.lang.String value) {
          addCriterion("uheadurl", value, ConditionMode.GREATER_THEN, "uheadurl", "java.lang.String", "String");
          return this;
      }

      public ReplyCriteria andUheadurlGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("uheadurl", value, ConditionMode.GREATER_EQUAL, "uheadurl", "java.lang.String", "String");
          return this;
      }

      public ReplyCriteria andUheadurlLessThan(java.lang.String value) {
          addCriterion("uheadurl", value, ConditionMode.LESS_THEN, "uheadurl", "java.lang.String", "String");
          return this;
      }

      public ReplyCriteria andUheadurlLessThanOrEqualTo(java.lang.String value) {
          addCriterion("uheadurl", value, ConditionMode.LESS_EQUAL, "uheadurl", "java.lang.String", "String");
          return this;
      }

      public ReplyCriteria andUheadurlBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("uheadurl", value1, value2, ConditionMode.BETWEEN, "uheadurl", "java.lang.String", "String");
    	  return this;
      }

      public ReplyCriteria andUheadurlNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("uheadurl", value1, value2, ConditionMode.NOT_BETWEEN, "uheadurl", "java.lang.String", "String");
          return this;
      }
        
      public ReplyCriteria andUheadurlIn(List<java.lang.String> values) {
          addCriterion("uheadurl", values, ConditionMode.IN, "uheadurl", "java.lang.String", "String");
          return this;
      }

      public ReplyCriteria andUheadurlNotIn(List<java.lang.String> values) {
          addCriterion("uheadurl", values, ConditionMode.NOT_IN, "uheadurl", "java.lang.String", "String");
          return this;
      }
	public ReplyCriteria andRcontentIsNull() {
		isnull("rcontent");
		return this;
	}
	
	public ReplyCriteria andRcontentIsNotNull() {
		notNull("rcontent");
		return this;
	}
	
	public ReplyCriteria andRcontentIsEmpty() {
		empty("rcontent");
		return this;
	}

	public ReplyCriteria andRcontentIsNotEmpty() {
		notEmpty("rcontent");
		return this;
	}
        public ReplyCriteria andRcontentLike(java.lang.String value) {
    	   addCriterion("rcontent", value, ConditionMode.FUZZY, "rcontent", "java.lang.String", "String");
    	   return this;
      }

      public ReplyCriteria andRcontentNotLike(java.lang.String value) {
          addCriterion("rcontent", value, ConditionMode.NOT_FUZZY, "rcontent", "java.lang.String", "String");
          return this;
      }
      public ReplyCriteria andRcontentEqualTo(java.lang.String value) {
          addCriterion("rcontent", value, ConditionMode.EQUAL, "rcontent", "java.lang.String", "String");
          return this;
      }

      public ReplyCriteria andRcontentNotEqualTo(java.lang.String value) {
          addCriterion("rcontent", value, ConditionMode.NOT_EQUAL, "rcontent", "java.lang.String", "String");
          return this;
      }

      public ReplyCriteria andRcontentGreaterThan(java.lang.String value) {
          addCriterion("rcontent", value, ConditionMode.GREATER_THEN, "rcontent", "java.lang.String", "String");
          return this;
      }

      public ReplyCriteria andRcontentGreaterThanOrEqualTo(java.lang.String value) {
          addCriterion("rcontent", value, ConditionMode.GREATER_EQUAL, "rcontent", "java.lang.String", "String");
          return this;
      }

      public ReplyCriteria andRcontentLessThan(java.lang.String value) {
          addCriterion("rcontent", value, ConditionMode.LESS_THEN, "rcontent", "java.lang.String", "String");
          return this;
      }

      public ReplyCriteria andRcontentLessThanOrEqualTo(java.lang.String value) {
          addCriterion("rcontent", value, ConditionMode.LESS_EQUAL, "rcontent", "java.lang.String", "String");
          return this;
      }

      public ReplyCriteria andRcontentBetween(java.lang.String value1, java.lang.String value2) {
    	  addCriterion("rcontent", value1, value2, ConditionMode.BETWEEN, "rcontent", "java.lang.String", "String");
    	  return this;
      }

      public ReplyCriteria andRcontentNotBetween(java.lang.String value1, java.lang.String value2) {
          addCriterion("rcontent", value1, value2, ConditionMode.NOT_BETWEEN, "rcontent", "java.lang.String", "String");
          return this;
      }
        
      public ReplyCriteria andRcontentIn(List<java.lang.String> values) {
          addCriterion("rcontent", values, ConditionMode.IN, "rcontent", "java.lang.String", "String");
          return this;
      }

      public ReplyCriteria andRcontentNotIn(List<java.lang.String> values) {
          addCriterion("rcontent", values, ConditionMode.NOT_IN, "rcontent", "java.lang.String", "String");
          return this;
      }
	public ReplyCriteria andRtimeIsNull() {
		isnull("rtime");
		return this;
	}
	
	public ReplyCriteria andRtimeIsNotNull() {
		notNull("rtime");
		return this;
	}
	
	public ReplyCriteria andRtimeIsEmpty() {
		empty("rtime");
		return this;
	}

	public ReplyCriteria andRtimeIsNotEmpty() {
		notEmpty("rtime");
		return this;
	}
       public ReplyCriteria andRtimeEqualTo(java.util.Date value) {
          addCriterion("rtime", value, ConditionMode.EQUAL, "rtime", "java.util.Date", "String");
          return this;
      }

      public ReplyCriteria andRtimeNotEqualTo(java.util.Date value) {
          addCriterion("rtime", value, ConditionMode.NOT_EQUAL, "rtime", "java.util.Date", "String");
          return this;
      }

      public ReplyCriteria andRtimeGreaterThan(java.util.Date value) {
          addCriterion("rtime", value, ConditionMode.GREATER_THEN, "rtime", "java.util.Date", "String");
          return this;
      }

      public ReplyCriteria andRtimeGreaterThanOrEqualTo(java.util.Date value) {
          addCriterion("rtime", value, ConditionMode.GREATER_EQUAL, "rtime", "java.util.Date", "String");
          return this;
      }

      public ReplyCriteria andRtimeLessThan(java.util.Date value) {
          addCriterion("rtime", value, ConditionMode.LESS_THEN, "rtime", "java.util.Date", "String");
          return this;
      }

      public ReplyCriteria andRtimeLessThanOrEqualTo(java.util.Date value) {
          addCriterion("rtime", value, ConditionMode.LESS_EQUAL, "rtime", "java.util.Date", "String");
          return this;
      }

      public ReplyCriteria andRtimeBetween(java.util.Date value1, java.util.Date value2) {
    	  addCriterion("rtime", value1, value2, ConditionMode.BETWEEN, "rtime", "java.util.Date", "String");
    	  return this;
      }

      public ReplyCriteria andRtimeNotBetween(java.util.Date value1, java.util.Date value2) {
          addCriterion("rtime", value1, value2, ConditionMode.NOT_BETWEEN, "rtime", "java.util.Date", "String");
          return this;
      }
        
      public ReplyCriteria andRtimeIn(List<java.util.Date> values) {
          addCriterion("rtime", values, ConditionMode.IN, "rtime", "java.util.Date", "String");
          return this;
      }

      public ReplyCriteria andRtimeNotIn(List<java.util.Date> values) {
          addCriterion("rtime", values, ConditionMode.NOT_IN, "rtime", "java.util.Date", "String");
          return this;
      }
	public ReplyCriteria andDelIsNull() {
		isnull("del");
		return this;
	}
	
	public ReplyCriteria andDelIsNotNull() {
		notNull("del");
		return this;
	}
	
	public ReplyCriteria andDelIsEmpty() {
		empty("del");
		return this;
	}

	public ReplyCriteria andDelIsNotEmpty() {
		notEmpty("del");
		return this;
	}
       public ReplyCriteria andDelEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andDelNotEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.NOT_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andDelGreaterThan(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.GREATER_THEN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andDelGreaterThanOrEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.GREATER_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andDelLessThan(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.LESS_THEN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andDelLessThanOrEqualTo(java.lang.Integer value) {
          addCriterion("del", value, ConditionMode.LESS_EQUAL, "del", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andDelBetween(java.lang.Integer value1, java.lang.Integer value2) {
    	  addCriterion("del", value1, value2, ConditionMode.BETWEEN, "del", "java.lang.Integer", "Float");
    	  return this;
      }

      public ReplyCriteria andDelNotBetween(java.lang.Integer value1, java.lang.Integer value2) {
          addCriterion("del", value1, value2, ConditionMode.NOT_BETWEEN, "del", "java.lang.Integer", "Float");
          return this;
      }
        
      public ReplyCriteria andDelIn(List<java.lang.Integer> values) {
          addCriterion("del", values, ConditionMode.IN, "del", "java.lang.Integer", "Float");
          return this;
      }

      public ReplyCriteria andDelNotIn(List<java.lang.Integer> values) {
          addCriterion("del", values, ConditionMode.NOT_IN, "del", "java.lang.Integer", "Float");
          return this;
      }
}