/*
 * CopyRight (c) 2018, 2019, LovelyAnQi
 * Script: Communication Oriented Objectified Toolkit
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package lovelyanqi.script;

import java.util.Map;

/**
 * <b> LovelyAnQi.Script Framework <i>Script CopyRight V2.3</i></b>
 * <br /><br />
 * class <code>Script</code> is the core unit of the
 * <b><i>LovelyAnQi Script</i></b> framework.
 * <br />
 * The core of <b>Script framework</b> is to unify <i>Java basic
 * types</i> and <i>Java functions</i>.
 * <br />
 * The Script framework is dedicated to improving the linkages of all data types.
 * <br />
 * Script also provides Java with more flexible pointer types.
 * <br /><br />
 * We can define a Script object in a very flexible way:
 * <blockquote><pre>
 *     Script sp = new Script();	//si.typeof() equals <b>Undefined</b>	sp == Script::Undefined
 *     Script si = new Script(null);	//si.typeof() equals <b>Null</b>	si equals null
 *     Script sn = new Script(0);	//sn.typeof() equals <b>Number</b>	sn equals 0
 *     Script sb = new Script(true);	//sb.typeof() equals <b>Boolean</b>	sb equals true
 *     Script ss = new Script("str");	//ss.typeof() equals <b>String</b>	ss equals "str"
 * </pre></blockquote>
 * You can also use the above types to combine more advanced types:
 * <blockquote><pre>
 *     Script array = Script.Array(0, 1, 2, true, "Script");
 *     //array.typeof() equals <b>Array</b>, array.toString() equals "[0, 1, 2, true, "Script"]"
 *     
 *     Script object = Script.ListObject("attr0", true, "attr1", "-1");
 *     //object.typeof() equals <b>Object</b>, object.toString() equals "{attr0: true, attr1: "-1"}"
 * </pre></blockquote>
 * Of course, combined high-level types can still combine new script elements:
 * <blockquote><pre>
 *     Script array = Script.Array(0, 1);
 *     
 *     Script xarray = Script.Array(array, array);
 *     //xarray.typeof() equals <b>Array</b>, xarray.toString() equals "[[0, 1], [0, 1]]"
 * </pre></blockquote>
 * You can even use script to build anonymous functions:
 * <blockquote><pre>
 *     //function.typeof() equals <b>Function</b>
 *     //function.toString() equals "function()=> { Script Anonymous Function (Simple Printing) }"
 *     Script function = Script.Function(new Script() {
 *         @Override
 *         public void run(Script res) { System.out.println(res); }
 *         
 *         @Override
 *         public String toString() { return "Script Anonymous Function (Simple Printing)"; }
 *     });
 * </pre></blockquote>
 * You can also use script to build pointers:
 * <blockquote><pre>
 *     Script pointer = new Script(0.1);
 *     //poiner.typeof() equals <b>Pointer</b>, pointer.toString() equals "0.1"
 * </pre></blockquote>
 * There is no doubt that pointers and anonymous functions can also be used to construct
 * the above advanced types.
 * <br /><br />
 * <b><i>Script CopyRight V2.3  @author LovelyAnQi 2019.05.16</i></b>
 */
public class Script {
	/**
	 * LovelyAnQi.Script <b>class <i>Script</i></b> Version
	 * <br />
	 * The Script Framework version and the underlying firmware
	 * version are independent of each other.
	 * <br />
	 * The Script Framework will select the specified stable
	 * firmware to launch the new version.
	 */
	public static final String CopyRight = "LovelyAnQi.Script.Script [版本 V2.3 2019.05.16]";
	
	/**
	 * <b>Type filter</b><br />
	 * Every script element has a fixed type
	 */
	private enum Type {
		Undefined, Null, String, Number, Boolean, Array, Pointer, Object, Function
	}
	
	/**
	 * <b>constant lock</b><br />
	 * If the constant lock is activated, the let operation of the
	 * script element will be locked.
	 */
	private boolean isConstant;
	
	/**
	 * <b>Script Core Data Elements</b><br />
	 * The data element entity of Script will be saved from this variable.
	 */
	private Object script;
	
	/**
	 * <b>Script Element Type</b><br />
	 * A script element can only be one of the nine types identified.
	 */
	private Type type;
	
	/**
	 * <b>Public field: <i>True</i></b><br />
	 * Common Constants Defined by Script = <b>Script::Boolean(false)</b><br />
	 * This public field cannot be assigned, and references to this public field
	 * do not create new objects.
	 */
	public static final Script False = Script.Boolean(false).lock();
	
	/**
	 * <b>Public field: <i>Null</i></b><br />
	 * Common Constants Defined by Script = <b>Script::Null</b><br />
	 * This public field cannot be assigned, and references to this public field
	 * do not create new objects.
	 */
	public static final Script Null = Script.Null().lock();

	/**
	 * <b>Public field: <i>True</i></b><br />
	 * Common Constants Defined by Script = <b>Script::Boolean(true)</b><br />
	 * This public field cannot be assigned, and references to this public field
	 * do not create new objects.
	 */
	public static final Script True = Script.Boolean(true).lock();
	
	/**
	 * <b>Constant Lock</b><br />
	 * The return value of a constant lock is the element itself, and a constant
	 * lock is applied to the element.
	 * <br /><br />
	 * The following types of elements become constants, that is, they cannot be
	 * changed:<br />
	 * <b> { Undefined, Null, String, Number, Boolean, Pointer, Function }</b>
	 * <br /><br />
	 * The types of elements of the following types will be locked, that is, their
	 * types cannot be changed:<br />
	 * <b> { Array, Object } </b>
	 */
	public final Script lock() {
		//Check the validity of variable initialization
		this.checkUndefined("lock()");
		
		this.isConstant = true;
		return this;
	}
	
	/**
	 * <b>Unlocking Constant</b><br />
	 * Unlock the constant lock of the element to restore the normal variable.
	 */
	public final Script unlock() {
		//Check the validity of variable initialization
		this.checkUndefined("unlock()");
		
		this.isConstant = false;
		return this;
	}

	/**********************************************************
	                      Constructor Set
	 **********************************************************/
	
	/**
	 * <b>Default Constructor</b><br />
	 * Private constructor, one-step initialization using specified initial state.
	 */
	private Script(Object script, Type type) {
		super();					//Call Object.Object()
		
		/** Using the incoming parameter to construct a Script element */
		this.script = script;
		this.type = type;
		
		this.isConstant = false;	//Default = false
	}
	
	/**
	 * <b>Default Constructor</b><br />
	 * The default constructor completes all initialization of a new script element.
	 * <br />
	 * Script() initializes a script element of <b>Undefined</b> type.
	 * <blockquote><pre>
	 *     Script sp = new Script();	//sp.typeof() equals "Undefined"
	 * </pre></blockquote>
	 * An Undefined variable <i>is allowed to be assigned</i>, but no other operation
	 * is allowed.
	 */
	public Script() {
		this(null, Type.Undefined);			//Construct An Undefined Script Element
	}
	
	/**
	 * <b>Constructing Method of Specified Object</b><br />
	 * Construct a Script element with any specified object.<br>
	 * Note: <i>It is generally believed that calling static generation methods of
	 * corresponding types to construct Script elements is more efficient than calling
	 * Script(Object) constructors.</i>.
	 * <br /><br />
	 * For different target objects, Script(Object) will use different initialization
	 * methods, the specific format is as follows:<br />
	 * For <i>null</i>, Script initializes a Script element of type Null.<br />
	 * For <i>an object of type Script</i>, Script will clone the object on this new 
	 * instance.
	 * <br /><br />
	 * Specifically, <i>Script provides more parsing types than let() method</i>.<br />
	 * For <i>{ int, boolean, String }</i>, Script is initialized to the corresponding
	 * <i>Number, Boolean, String</i> Script elements according to the given value.<br />
	 * For <i>{ int[], boolean[], String[] }</i>, Script is initialized to an Array of
	 * corresponding types.
	 * <br /><br />
	 * Warning: <i>Other types are converted to Script pointers</i>.
	 */
	public Script(Object object) {
		this(null, Type.Undefined);			//Default initialization
		
		/**
		 * Directional instantiation: Null
		 * Initialize a type of Script element for Null.
		 */
		if (object == null) {
			this.type = Type.Null;	//Relocation
			return;					//Initialization complete
		}
		
		/** Equivalent to cloning a new script element */
		if (object instanceof Script) {		//Clonal structure
			/** Cloning a copy of the current instance using the target script instance */
			this.Clone((Script)object);			//Call the cloning method
			return;					//Initialization complete
			
			/** Building a new script element with a given Java member */
		} else {								//Create new script elements			
			/**
			 * Script provides more parsing types than let() method
			 * 1. int
			 * 2. boolean
			 * 3. String
			 * 4. int[]
			 * 5. boolean[]
			 * 6. String[]
			 */
			
			/* Processing target data of type int */
			if (object instanceof Integer) {
				this.let(object);
				
				/* Processing target data of type boolean */
			} else if (object instanceof Boolean) {
				this.let(object);
				
				/* Processing target data of type String */
			} else if (object instanceof String) {
				this.let(object);
				
				/* Processing target data of type int[] */
			} else if (object instanceof int[]) {
				this.type = Type.Array;			//Type relocation
				int[] nativeNumberArray = (int[])object;
				//Dependence on Java Standard Library
				java.util.ArrayList<Script> nativeArray = new java.util.ArrayList<Script>();
				
				for (int index = 0; index < nativeNumberArray.length; index++) {
					/** Native acceleration */
					nativeArray.add(Script.Number(nativeNumberArray[index]));
				}
				this.script = nativeArray;
				
				/* Processing target data of type boolean[] */
			} else if (object instanceof Boolean[]) {
				this.type = Type.Array;			//Type relocation
				boolean[] nativeBooleanArray = (boolean[])object;
				//Dependence on Java Standard Library
				java.util.ArrayList<Script> nativeArray = new java.util.ArrayList<Script>();
				
				for (int index = 0; index < nativeBooleanArray.length; index++) {
					/** Native acceleration */
					nativeArray.add(Script.Boolean(nativeBooleanArray[index]));
				}
				this.script = nativeArray;
				
				/* Processing target data of type String */
			} else if (object instanceof String[]) {
				this.type = Type.Array;			//Type relocation
				String[] nativeStringArray = (String[])object;
				//Dependence on Java Standard Library
				java.util.ArrayList<Script> nativeArray = new java.util.ArrayList<Script>();
				
				for (int index = 0; index < nativeStringArray.length; index++) {
					/** Native acceleration */
					nativeArray.add(Script.String(nativeStringArray[index]));
				}
				this.script = nativeArray;
				
				/** Other types are converted to script pointers */
			} else {
				this.type = Type.Pointer;		//Type relocation
				this.script = object;
			}
		}
	}
	/**********************************************************/
	
	
	/**********************************************************
	          Type Primitive Generation Component Set
	 **********************************************************/
	
	/**
	 * <b>Script Type Constructor</b><br />
	 * Undefined() returns a fully initialized script element of type <b>Undefined</b>.
	 * <br /><br />
	 * Note: Script variables are unique for the Undefined type.
	 * <br /><br />
	 * Warning: <i>Undefined variables cannot perform any operations other than
	 * deletion, assignment, type testing, constant testing.</i>
	 */
	public static final Script Undefined() {
		
		/** Native acceleration */
		return new Script(null, Type.Undefined);
	}
	
	/**
	 * <b>Script Type Constructor</b><br />
	 * Null() returns a fully initialized script element of type <b>Null</b>.
	 * <br />
	 * Note: Script variables are unique for the Null type.
	 */
	public static final Script Null() {

		/** Native acceleration */
		return new Script(null, Type.Null);				//The Starting Point of All Script Elements
	}
	
	/**
	 * <b>Script Type Constructor</b><br />
	 * String() returns a Script element of type <b>String</b>.<br />
	 * The generated Script::String will be the same as the string in the parameter.
	 * <br /><br />
	 * Warning: <i>If the incoming object is null, String() generates <b>an empty string</b></i>.
	 */
	public static final Script String(String string) {
		
		String nativeString = (string == null ? new String("") : string);
		/** Native acceleration */
		return new Script(nativeString, Type.String);		//The Starting Point of All Script Elements
	}
	
	/**
	 * <b>Script Type Constructor</b><br />
	 * Number() returns a Script element of type <b>Number</b>.<br />
	 * The generated Script::Number will be the same as the number in the parameter.
	 * <br /><br />
	 * Warning: <i>If the incoming object is null, Number() generates a Script::Number
	 * <br />with the number <b>0</b></i>.
	 */
	public static final Script Number(Integer number) {
		
		Integer nativeNumber = (number == null ? Integer.valueOf(0) : number);
		/** Native acceleration */
		return new Script(nativeNumber, Type.Number);		//The Starting Point of All Script Elements
	}
	
	/**
	 * <b>Script Type Constructor</b><br />
	 * Boolean() returns a Script element of type <b>Boolean</b>.<br />
	 * The generated Script::Boolean will be the same as the truth in the parameter.
	 * <br /><br />
	 * Warning: <i>If the incoming object is null, Boolean() generates a Script::Boolean
	 * <br />with the booelan value <b>false</b></i>.
	 */
	public static final Script Boolean(Boolean truth) {
		
		Boolean nativeBoolean = (truth == null ? Boolean.FALSE : truth);
		/** Native acceleration */
		return new Script(nativeBoolean, Type.Boolean);	//The Starting Point of All Script Elements
	}
	
	/**
	 * <b>Script Type Constructor</b><br />
	 * Function() returns a Script element of type <b>Function</b>.<br />
	 * The essence of a function is a pointer to an object that contains
	 * an override of the run method.
	 * <br /><br />
	 * Warning: <i>You can pass in an instance of a subclass of Script in which its 
	 * <b>run(Script)</b> method and <b>toString()</b> method are rewritten.
	 * <br />
	 * Once null is passed in, Function automatically generates an empty function.</i>
	 */
	public static final Script Function(Script function) {
		/** Native acceleration */
		Script nativeScript = new Script(null, Type.Function);				//The Starting Point of All Script Elements
		
		/** The essence of a function is a pointer to an object that contains an override of the run method */
		nativeScript.script = function == null ? new Script() {
			@Override
			public void run(Script res) {}
			
			@Override
			public String toString() {
				return "System Null Function";
			}
		} : function;
		
		return nativeScript;
	}
	
	/**
	 * <b>Script Type Constructor</b><br />
	 * Pointer() returns a Script element of type <b>Pointer</b>.<br />
	 * Construct a pointer to any object that can even be Script.
	 * <br /><br />
	 * Warning: <i>If null is passed in, Pointer points the pointer to Script.Undefined()</i>.
	 */
	public static final Script Pointer(Object object) {
		/** Native acceleration */
		Script nativeScript = new Script(Script.Undefined(), Type.Pointer);	//The Starting Point of All Script Elements
		
		if (object != null) nativeScript.script = object;
		
		return nativeScript;
	}
	
	/**
	 * <b>Script Type Constructor</b><br />
	 * Array() returns a Script element of type <b>Array</b>.
	 * <br /><br />
	 * This method is the native construction method of <i>Script::Array</i> and
	 * the starting point constructor of all Script::Array.
	 * <br /><br />
	 * If the specified parameter is <i>null or no</i>, Array() constructs an 
	 * <i>empty array</i>.
	 * <br /><br />
	 * Array() builds a Script Array in the order in which the parameters are passed in,
	 * The time complexity of construction is generally <b><i>O(n)</i></b>.
	 * <br /><br />
	 * Array() constructs arrays in the same way as the following code:
	 * <blockquote><pre>
	 *     for (int index = 0; index < objects.length; index++) {
	 *         this.append(new Script(objects[index]));
	 *     }
	 * </pre></blockquote>
	 * Obviously, Array() constructs <i>copies</i> of each object in turn.
	 */
	public static final Script Array(Object... objects) {
		/** Native acceleration */
		Script nativeScript = new Script(null, Type.Array);					//The Starting Point of All Script Elements
		
		//Dependence on Java Standard Library
		java.util.ArrayList<Script> nativeArray = new java.util.ArrayList<Script>();
		
		/** Array generation */
		if (objects != null && objects.length != 0) {	//Create non-empty arrays
			for (int index = 0; index < objects.length; index++) {
				nativeArray.add(new Script(objects[index]));
			}
		}
		nativeScript.script = nativeArray;
		
		return nativeScript;
	}
	
	/**
	 * <b>Script Type Constructor</b><br />
	 * Object() returns a Script element of type <b>Object</b>.
	 * <br /><br />
	 * This method is the native construction method of <i>Script::Object</i> and
	 * the starting point constructor of all Script::Object.
	 * <br /><br />
	 * Note: <i>You can call <b>mem()</b> to add key-value pairs to Object,
	 * or you can call <b>delete()</b> to delete the corresponding key-value pairs.</i>
	 * <br /><br />
	 * Sample code:
	 * <blockquote><pre>
	 *     Script object = Script.Object();		//object.typeof() equals "Object"
	 *     
	 *     object.mem("test").let("value");		//Adding key-value pairs <"test", "value">
	 *     
	 *     object.mem("test").delete();		//Delete the key "test" and its corresponding value
	 * </pre></blockquote>
	 * You can also call the clear() method to clear all key-value pairs.
	 */
	public static final Script Object() {
		/** Native acceleration */
		Script nativeScript = new Script(null, Type.Object);					//The Starting Point of All Script Elements
		
		//Dependence on Java Standard Library
		java.util.Map<Script, Script> nativeObject = new java.util.HashMap<Script, Script>();
		
		nativeScript.script = nativeObject;
		
		return nativeScript;
	}
	/**********************************************************/
	

	/**********************************************************
	             Convenient Construction of Script
	 **********************************************************/
	
	/**
	 * <b>Script Convenient Construction</b><br />
	 * ListObject() allows you to quickly construct an Object using a variable-length parameter list.
	 * <br /><br />
	 * You can use the following code to complete this convenient operation:
	 * <blockquote><pre>
	 *     Script object = Script.ListObject(
	 *         "attribute", "features",
	 *         "key", "value"
	 *     );
	 * </pre></blockquote>
	 * ListObject receives even numbers of parameters and divides them into two parts in order.
	 * The first will be used as a key and the second will be used as a value.
	 */
	public static final Script ListObject(Object... objects) {
		
		/** Check the validity of parameters */
		if (objects == null || objects.length % 2 != 0) {						//Judging Legitimacy
			throw new IllegalArgumentException(
					"Parameters cannot be null and must be even"
			);
		}
		
		/** Native acceleration */
		Script nativeScript = new Script(null, Type.Object);					//The Starting Point of All Script Elements
		
		/** Object generation */
		Script key;
		//Dependence on Java Standard Library
		java.util.Map<Script, Script> nativeObject = new java.util.HashMap<Script, Script>();
		for (int i = 0; i < objects.length; i += 2) {
			if (!checkKeyType(key = new Script(objects[i]))) {
				throw new RuntimeException(
						"Key Script " + key + " cannot be an Object key"
				);
			}
			nativeObject.put(key, new Script(objects[i + 1]));
		}
		nativeScript.script = nativeObject;
		
		return nativeScript;
	}
	
	/**
	 * <b>Script Convenient Construction</b><br />
	 * TemplateObject() allow you to construct a template for an object with variable length parameters.
	 * <br /><br />
	 * TemplateObject() will construct an object that uses all the contents specified in the parameter
	 * as keys, and their values will be initialized to Null.
	 */
	public static final Script TemplateObject(Object... keys) {
		/** Native acceleration */
		Script nativeScript = new Script(null, Type.Object);					//The Starting Point of All Script Elements
		
		Script key;
		//Dependence on Java Standard Library
		java.util.Map<Script, Script> nativeObject = new java.util.HashMap<Script, Script>();
		
		/** Object generation */
		for (int i = 0; keys != null && i < keys.length; i++) {
			if (!checkKeyType(key = new Script(keys[i]))) {
				throw new RuntimeException(
						"Key Script " + key + " cannot be an Object key"
				);
			}
			nativeObject.put(key, Script.Null);
		}
		nativeScript.script = nativeObject;
		
		return nativeScript;
	}
	/**********************************************************/
	
	
	/**********************************************************
	                  Script Common Method Set
	 **********************************************************/
	
	/**
	 * <i>Script::checkConstant(String)</i><br />
	 * checkConstant() checks whether the current variable is a constant,
	 * and if so, checkConstant() throws an exception.
	 */
	private final void checkConstant(String caller) {
		if (this.isConstant()) {
			throw new RuntimeException(
					caller + " method cannot be invoked on a constant"
			);
		}
	}
	
	/**
	 * <i>Script::checkUndefined()</i><br />
	 * checkUndefined() checks whether the current variable is an Undefined variable,
	 * and if so, checkUndefined() throws an exception.
	 */
	private final void checkUndefined(String caller) {
		if (this.type == Type.Undefined) {		//Native Code Acceleration
			throw new RuntimeException(
					caller + " method cannot be invoked on an Undefined variable"
			);
		}
	}
	
	/**
	 * <i>Script::checkType()</i><br />
	 * checkType() method is used to check whether a type can be used to
	 * legally invoke a specified method.
	 */
	private final void checkType(String caller, Type test) {
		if (this.type != test) {				//Native Code Acceleration
			throw new RuntimeException(
					caller + " method cannot be invoked on non-" + test.toString()
			);	
		}
	}
	
	/**
	 * <i>Script::typeof()</i><br />
	 * Returns a string that refers to the current script element type.
	 */
	public final String typeof() {
		return this.type.toString();
	}
	
	/**
	 * <i>Script::typeof(String)</i><br />
	 * Test whether the type of the current script element is equivalent
	 * to the incoming string.
	 * <br /><br />
	 * The test process is equivalent to the following code:
	 * <blockquote><pre>
	 *     return this.typeof().equalsIgnoreCase(testType);
	 * </pre></blockquote>
	 * Warning: <i>The test process ignores case letters</i>.
	 */
	public final boolean typeof(String testType) {
		return this.typeof().equalsIgnoreCase(testType);
	}
	
	/**
	 * <i>Script::isConstant()</i><br />
	 * Gets the constant properties of the current script element.<br />
	 * If it is a constant, isConstant() returns true, otherwise false.
	 */
	public final boolean isConstant() {
		if (this.type == Type.Undefined) {		//Native Code Acceleration
			return false;				//Undefined variables cannot be constants.
		}
		
		return this.isConstant;
	}
	
	/**
	 * <i>Script::delete()</i><br />
	 * Reset the current Script element.<br />
	 * The type of the deleted script element changes to <b>Undefined</b>.
	 * <br /><br />
	 * Specifically: <i>Calling the delete() method on a member of
	 * Script::Object deletes that member from Object</i>.
	 */
	public final void delete() {
		//Check Constant Legitimacy
		this.checkConstant("delete()");
		
		this.script = null;
		this.type = Type.Undefined;
	}
	
	/**
	 * <i>Script::del()</i><br />
	 * Reset the current Script element.<br />
	 * The type of the deleted script element changes to <b>Undefined</b>.
	 * <br /><br />
	 * Specifically: <i>Calling the del() method on a member of
	 * Script::Object deletes that member from Object</i>.
	 */
	public final void del() {
		//Relocation
		this.delete();
	}
	
	/**
	 * <i>Script::Clone()</i><br />
	 * Returns a copy of the current object, equivalent to the following code:
	 * <blockquote><pre>
	 *     return new Script(this);
	 * </pre></blockquote>
	 * Warning: <i>The Script element of Undefined cannot be cloned</i>.
	 * <br><i>Generally speaking, the following code is faster than
	 * calling this method and achieves the same effect:</i>
	 * <blockquote><pre>
	 *     Script script = new Script("test");
	 *     Script thisClone = script.Clone();			//This method
	 *     Script consturctorClone = new Script(script);	//Using constructive methods
	 *     Script relocationClone = new Script().Clone(script);//Use relocation
	 * </pre></blockquote>
	 * The latter two methods are faster than the present one. In fact, the gap is very small.
	 */
	public final Script Clone() {
		//Check the validity of variable initialization
		this.checkUndefined("Clone()");
		
		return new Script(this);	//Clone() method is equivalent to calling new Script(this)
	}
	
	/**
	 * <i>Script::Clone(Script)</i><br />
	 * Create a copy of the specified script element on the current script
	 * element using a script element.
	 * <br /><br />
	 * Equivalent to assigning current script elements using target script elements.
	 * <br /><br />
	 * Warning: <i>Cloning does not preserve the constant property of the target
	 * script element, that is, the script element must not be constant after Clone,
	 * and if the script element is constant, cloning other script elements on the
	 * script element is not allowed</i>.
	 */
	public final Script Clone(Script source) {
		//Check Constant Legitimacy
		this.checkConstant("Clone(Script)");
		
		/** Cloning also belongs to an assignment */
		
		if (source == null) {					//null is equivalent to Script.Null
			this.script = null;
			this.type = Type.Null;				//Type relocation
			return this;						//Clone completed
		}
		
		/* Composite data types */
		if (source.type == Type.Array) {		//Array parsing
			
			@SuppressWarnings("unchecked")
			//Get the data structure itself of the target Array
			java.util.ArrayList<Script> externArray = (java.util.ArrayList<Script>)source.script;
			java.util.Iterator<Script> externArrayIterator = externArray.iterator();	//Obtaining iterators
			java.util.ArrayList<Script> nativeArray = new java.util.ArrayList<Script>();
			
			/* Iterate the original Array sequentially */
			while (externArrayIterator.hasNext()) {
				/**
				 * Calling new Script(Script) directly is more efficient than calling script.Clone()
				 */
				nativeArray.add(new Script(externArrayIterator.next()));
			}
			
			this.script = nativeArray;
			this.type = Type.Array;				//Type relocation
			return this;						//Clone completed
		}
		
		/* Composite data types */
		if (source.type == Type.Object) {		//Object parsing
			
			@SuppressWarnings("unchecked")
			//Get the data structure itself of the target Object
			java.util.Map<Script, Script> externObject = (java.util.HashMap<Script, Script>)source.script;
			/**
			 * Get Entry iterator of Map
			 * Native and Efficient Iteration Scheme
			 */
			java.util.Iterator<java.util.Map.Entry<Script, Script>> externObjectIterator = externObject.entrySet().iterator();
			java.util.Map<Script, Script> nativeObject = new java.util.HashMap<Script, Script>();
			
			java.util.Map.Entry<Script, Script> nativeEntry;
			/* Iterate the original Object sequentially */
			while (externObjectIterator.hasNext()) {
				nativeEntry = externObjectIterator.next();
				
				if (nativeEntry.getValue().type == Type.Undefined) {
					continue;					//Undefined type key pairs will not be copied
				}
				
				/**
				 * Calling new Script(Script) directly is more efficient than calling script.Clone()
				 */
				nativeObject.put(new Script(nativeEntry.getKey()), new Script(nativeEntry.getValue()));
			}

			this.script = nativeObject;
			this.type = Type.Object;			//Type relocation
			return this;						//Clone completed
		}
		
		/**
		 * Type as:	( those.script is constant )
		 * { Undefined, Null, String, Number, Boolean, Function, Pointer }
		 */
		this.script = source.script;
		this.type = source.type;
		
		return this;
	}
	
	/**
	 * <i>Script::let(Script)</i><br />
	 * Use an object to assign a value to the script element, making the Script
	 * element a new element.
	 * <br /><br />
	 * If <i>null</i> is passed in, let (Object) makes the current script element Script.Null.
	 * <br />
	 * If accepts <i>an instance of a Script class<i>, the let(Object) method calls the
	 * <b>Clone (Object)</b> method on this example.
	 * For the following types of parameters, let(Object) generates the corresponding Script:
	 * <br />
	 * <i> { int, boolean, String } </i>
	 */
	public final Script let(Object object) {
		//Check Constant Legitimacy
		this.checkConstant("let(Object)");
		
		/**
		 * Directional instantiation: Null
		 * Initialize a type of Script element for Null.
		 */
		if (object == null) {
			this.script = null;
			this.type = Type.Null;
			return this;				//Initialization complete
		}
		
		/** Equivalent to cloning a new script element */
		if (object instanceof Script) {		//Clonal structure
			/** Cloning a copy of the current instance using the target script instance */
			this.Clone((Script)object);			//Call the cloning method
			return this;				//Initialization complete
			
			/** Building a new script element with a given Java member */
		} else {
			
			/** let() type conversion when accepting assignments of only { int, boolean, String} */
			if (object instanceof Integer || object instanceof Boolean || object instanceof String) {
				this.script = object;
				this.type = object instanceof Integer ? Type.Number : object instanceof Boolean ? Type.Boolean : Type.String;
				
				/** Other types will be considered pointers */
			} else {
				this.script = object;
				this.type = Type.Pointer;		//Type relocation
			}
		}
		
		return this;							//Provide chain programming return values
	}
	
	/**
	 * <i>Serialization Generator</i>
	 * <br /><br />
	 * Fixed length format:<br />
	 * <i>Undefined = u, Null = n, Boolean = t/f, Number = i(00000000)(16)</i>
	 * <br /><br />
	 * Indefinite length format:<br />
	 * <i>String = s[Dec]*[string], Array = a[Dec]*[arrays], Object = o[Dec]*[objects]</i>
	 */
	private final java.util.ArrayList<Byte> passbytes(java.util.ArrayList<Byte> bytes) {
		
		/** Serialization type checking */
		if (this.type == Type.Pointer || this.type == Type.Function) {
			throw new RuntimeException(
					"Unsupported Serialization Type: " + this.typeof()
			);
		}
		
		/** Determine whether the array is initialized for the first time */
		if (bytes == null) {
			bytes = new java.util.ArrayList<Byte>();
		}
		
		/** Undefined is identified as u */
		if (this.type == Type.Undefined) {
			bytes.add((byte)'u');
			
			/** Null is identified as n */
		} else if (this.type == Type.Null) {
			bytes.add((byte)'n');
			
			/** Boolean is identified as t(true) or f(false) */
		} else if (this.type == Type.Boolean) {
			Boolean booleanValue = (Boolean)this.script;
			bytes.add(booleanValue ? (byte)'t' : (byte)'f');
			
			/** Number is identified as i(00000000)(16) */
		} else if (this.type == Type.Number) {
			int intValue = (Integer)this.script;
			bytes.add((byte)'i');
			bytes.add((byte)(intValue >> 0x18));
			bytes.add((byte)((intValue >> 0x10) & 0xFF));
			bytes.add((byte)((intValue >> 0x08) & 0xFF));
			bytes.add((byte)(intValue & 0xFF));
		}
		
		/** Variable-length serialization algorithm for Strings */
		if (this.type == Type.String) {
			String stringValue = (String)this.script;
			try {
				byte[] stringBytes = stringValue.getBytes("UTF-8");
				bytes.add((byte)'s');
				String length = String.valueOf(stringBytes.length);
				byte[] lengthBytes = length.getBytes("ASCII");
				for (int i = 0; i < lengthBytes.length; i++) {
					bytes.add(lengthBytes[i]);
				}
				bytes.add((byte)'*');
				for (int i = 0; i < stringBytes.length; i++) {
					bytes.add(stringBytes[i]);
				}
			} catch (java.io.UnsupportedEncodingException e) {}
		}
		
		/** Combination Types: Serialization of Arrays */
		if (this.type == Type.Array) {
			@SuppressWarnings("unchecked")
			java.util.ArrayList<Script> nativeArray = (java.util.ArrayList<Script>)this.script;
			try {
				bytes.add((byte)'a');
				String length = String.valueOf(nativeArray.size());
				byte[] lengthBytes = length.getBytes("ASCII");
				for (int i = 0; i < lengthBytes.length; i++) {
					bytes.add(lengthBytes[i]);
				}
				bytes.add((byte)'*');
				for (int i = 0; i < nativeArray.size(); i++) {
					nativeArray.get(i).passbytes(bytes);
				}
			} catch (java.io.UnsupportedEncodingException e) {}
		}
		
		/** Combination Types: Serialization of Objects */
		if (this.type == Type.Object) {
			@SuppressWarnings("unchecked")
			java.util.Map<Script, Script> nativeObject = (java.util.Map<Script, Script>)this.script;
			try {
				bytes.add((byte)'o');
				String length = String.valueOf(nativeObject.size());
				byte[] lengthBytes = length.getBytes("ASCII");
				for (int i = 0; i < lengthBytes.length; i++) {
					bytes.add(lengthBytes[i]);
				}
				bytes.add((byte)'*');
				
				java.util.Iterator<java.util.Map.Entry<Script, Script>> nativeIterator = nativeObject.entrySet().iterator();
				java.util.Map.Entry<Script, Script> nativeEntry;
				while (nativeIterator.hasNext()) {
					nativeEntry = nativeIterator.next();
					nativeEntry.getKey().passbytes(bytes);
					nativeEntry.getValue().passbytes(bytes);
				}
			} catch (java.io.UnsupportedEncodingException e) {}
		}
		
		return bytes;
	}
	
	/**
	 * <i>Script::passbytes()</i><br />
	 * Get the serialized byte stream of a Script.
	 */
	public final byte[] passbytes() {
		
		/** Serialization Generator */
		java.util.ArrayList<Byte> arrayBytes = this.passbytes(null);
		
		/** Sequence Stream Reconstruction */
		byte[] bytes = new byte[arrayBytes.size()];
		for (int inc = 0; inc < bytes.length; inc++) {
			bytes[inc] = arrayBytes.get(inc);
		}
		
		return bytes;
	}
	
	/**
	 * <i>Script::Load(java.util.InputStream)</i><br />
	 * Native deserialization operation of script, which reads the <i>first Script element</i>
	 *  from the input stream for construction.
	 */
	public final static Script Load(java.io.InputStream bytes) {
		
		Script nativeScript = null;
		
		try {
			int lengthSeeker = 0, length;
			byte[] lengthBuff = new byte[0x10];
			switch (bytes.read()) {
			case 'u':
				nativeScript = Script.Undefined();
				break;
			case 'n':
				nativeScript = Script.Null();
				break;
			case 't':
				nativeScript = Script.Boolean(true);
				break;
			case 'f':
				nativeScript = Script.Boolean(false);
				break;
			case 'i':
				int nativeNumber = 0;
				for (int inc = 0; inc < 4; inc++) {
					nativeNumber <<= 0x08;
					nativeNumber += bytes.read();
				}
				nativeScript = Script.Number(nativeNumber);
				break;
			case 's':
				while ((lengthBuff[lengthSeeker++] = (byte)bytes.read()) != '*');
				length = Integer.valueOf(new String(lengthBuff, 0, lengthSeeker - 1, "ASCII"));
				byte[] stringBuffer = new byte[length];
				for (int inc = 0; inc < length; inc++) {
					stringBuffer[inc] = (byte)bytes.read();
				}
				nativeScript = Script.String(new String(stringBuffer, "UTF-8"));
				break;
			case 'a':
				while ((lengthBuff[lengthSeeker++] = (byte)bytes.read()) != '*');
				length = Integer.valueOf(new String(lengthBuff, 0, lengthSeeker - 1, "ASCII"));
				nativeScript = Script.Array();
				@SuppressWarnings("unchecked")
				java.util.ArrayList<Script> nativeArray = (java.util.ArrayList<Script>)nativeScript.script;
				while (length-- > 0) {
					nativeArray.add(Script.Load(bytes));
				}
				break;
			case 'o':
				while ((lengthBuff[lengthSeeker++] = (byte)bytes.read()) != '*');
				length = Integer.valueOf(new String(lengthBuff, 0, lengthSeeker - 1, "ASCII"));
				nativeScript = Script.Object();
				@SuppressWarnings("unchecked")
				java.util.Map<Script, Script> nativeObject = (java.util.Map<Script, Script>)nativeScript.script;
				while (length-- > 0) {
					nativeObject.put(Script.Load(bytes), Script.Load(bytes));
				}
				break;
			default:
				break;
			}
		} catch (java.io.IOException e) {}
		
		return nativeScript == null ? Script.Undefined() : nativeScript;
	}
	
	/**
	 * <i>Script::Load(byte[])</i><br />
	 * Constructing byte streams to generate Script elements using specified byte arrays.
	 */
	public final static Script Load(byte[] bytes) {
		/** Internal relocation */
		return Script.Load(bytes, 0, bytes.length);
	}
	
	/**
	 * <i>Script::Load(byte[], int offset, int length)</i><br />
	 * Construct a Script element using a given byte array and intercepting a
	 * segment as a byte stream.
	 */
	public final static Script Load(byte[] bytes, int offset, int length) {
		
		/** Build byte streams and generate script elements */
		return Script.Load(new java.io.ByteArrayInputStream(bytes, offset, length));
	}
	
	/**
	 * <i>Script::toString()</i><br />
	 * Generate a string representing a script element, which is generated in a specific format.
	 * <br /><br />
	 * For <i>Undefined</i>, toSting() simply returns the string "Undefined"<br />
	 * For <i>Null</i>, toSting() simply returns the string "Null"<br />
	 * For <i>String</i>, toString() returns the quoted form of this string<br />
	 * For <i>Number</i>, toString() returns the number itself<br />
	 * For <i>Boolean</i>, toString() returns Boolean expressions: [true / false ]
	 * <br /><br />
	 * For <i>Array</i>, toString() returns Array parsing format.<br />
	 * For <i>Object</i>, toString() returns Object parsing format.<br />
	 */
	@Override
	public java.lang.String toString() {
		//String Generator
		StringBuilder nativeString = new StringBuilder();
		
		/* Undefined data type */
		if (this.type == Type.Undefined) {			//Undefined data whose string is its type name
			nativeString.append("Undefined");
			
			/* Basic data types */
		} else if (this.type == Type.Null) {		//Null data whose string is its type name
			nativeString.append("Null");
			
			/* Basic data types */
		} else if (this.type == Type.Boolean) {	//String is its truth value
			Boolean nativeBoolean = (Boolean)this.script;
			nativeString.append(nativeBoolean.toString());
			
			/* Basic data types */
		} else if (this.type == Type.Function) {	//Function is its own toString() method
			nativeString.append("function()=> { ");
			nativeString.append(this.script.toString());
			nativeString.append(" }");
			
			/* Basic data types */
		} else if (this.type == Type.Number) {		//Number is its value (plus or minus and zero)
			Integer nativeInteger = (Integer)this.script;
			nativeString.append(nativeInteger.toString());
			
			/* Basic data types */
		} else if (this.type == Type.Pointer) {	//Pointer is its own toString() method
			nativeString.append("pointer-> { ");	
			nativeString.append(this.script.toString());
			nativeString.append(" }");
			
			/* Basic data types */
		} else if (this.type == Type.String) {	//String is the original stringwith quotation marks
			String _nativeString = (String)this.script;
			nativeString.append('\"');
			nativeString.append(_nativeString);
			nativeString.append('\"');
		}
		
		/* Composite data types */
		if (this.type == Type.Array) {				//Array parsing
			@SuppressWarnings("unchecked")
			java.util.ArrayList<Script> nativeArray = (java.util.ArrayList<Script>)this.script;
			java.util.Iterator<Script> nativeArrayIterator = nativeArray.iterator();	//Obtaining iterators
			
			nativeString.append('[');				//Parsing process
			while (nativeArrayIterator.hasNext()) {
				nativeString.append(nativeArrayIterator.next().toString());
				if (nativeArrayIterator.hasNext()) {
					nativeString.append(", ");
				}
			}
			nativeString.append(']');
		}
		
		/* Composite data types */
		if (this.type == Type.Object) {			//Object parsing
			@SuppressWarnings("unchecked")
			java.util.Map<Script, Script> nativeObject = (java.util.HashMap<Script, Script>)this.script;
			/**
			 * Get Entry iterator of Map
			 * Native and Efficient Iteration Scheme
			 */
			java.util.Iterator<java.util.Map.Entry<Script, Script>> nativeObjectIterator = nativeObject.entrySet().iterator();
			
			nativeString.append('{');				//Parsing process
			java.util.Map.Entry<Script, Script> nativeEntry = null;
			while (nativeObjectIterator.hasNext() || nativeEntry != null) {
				nativeEntry = nativeEntry == null ? nativeObjectIterator.next() : nativeEntry;
				if (nativeEntry.getValue().type == Type.Undefined) {
					nativeEntry = null;
					continue;
				}
				nativeString.append(nativeEntry.getKey().toString());
				nativeString.append(": ");
				nativeString.append(nativeEntry.getValue().toString());
				nativeEntry = null;
				
				while (nativeObjectIterator.hasNext()) {
					nativeEntry = nativeObjectIterator.next();
					if (nativeEntry.getValue().type == Type.Undefined) {
						continue;
					}
					nativeString.append(", ");
					break;
				}
			}
			nativeString.append('}');
		}
		
		//Returns the generated expression
		return nativeString.toString();
	}
	
	/**
	 * Internal testing
	 * Ensure that the incoming value is not null
	 */
	private boolean equalsScript(Script element) {
		
		/** Undefined differs from one another */
		if (element.type == Type.Undefined) {
			return false;
		}
		
		/** Type filter */
		if (element.type != this.type) {
			return false;
		}
		
		/** First Judge Null Value */
		if (this.type == Type.Null) {
			return true;
		}
		
		/* Null is not allowed in the latter comparison */
		
		/* For Array */
		if (element.type == Type.Array) {
			@SuppressWarnings("unchecked")
			java.util.ArrayList<Script> nativeArray = (java.util.ArrayList<Script>)this.script;
			@SuppressWarnings("unchecked")
			java.util.ArrayList<Script> externArray = (java.util.ArrayList<Script>)element.script;
			
			/* Size comparison */
			if (nativeArray.size() != externArray.size()) {
				return false;
			}
			
			int size = nativeArray.size();
			/** Deep recursion */
			for (int index = 0; index < size; index++) {
				if (!nativeArray.get(index).equals(externArray.get(index))) {
					return false;
				}
			}
			return true;
		}
		
		/* For Object */
		if (element.type == Type.Object) {
			@SuppressWarnings("unchecked")
			java.util.Map<Script, Script> nativeObject = (java.util.HashMap<Script, Script>)this.script;
			@SuppressWarnings("unchecked")
			java.util.Map<Script, Script> externObject = (java.util.HashMap<Script, Script>)element.script;
			
			java.util.Set<Script> checkedKeys = new java.util.HashSet<Script>();
			/** Deep recursion */
			Script nativeValue, externValue;
			for (Script it : nativeObject.keySet()) {
				
				/**
				 * Traversing through its own properties to determine whether a defined
				 * property has the same definition in the incoming object.
				 */
				if ((nativeValue = nativeObject.get(it)).type != Type.Undefined) {
					//Undefined equivalence to inequality
					if ((externValue = externObject.get(it)) == null) {
						return false;
					}
					
					//To further determine whether the two are equal
					if (!nativeValue.equals(externValue)) {
						return false;
					}
					checkedKeys.add(it);						//Represents that a Key has been determined
				}
			}
			
			/** Backward recursion */
			for (Script it : externObject.keySet()) {
				
				/**
				 * Skip keys that have been judged to be equal
				 */
				if (!checkedKeys.contains(it) && (externValue = externObject.get(it)).type != Type.Undefined) {
					//Undefined equivalence to inequality
					if ((nativeValue = nativeObject.get(it)) == null) {
						return false;
					}
					
					//To further determine whether the two are equal
					if (!externValue.equals(nativeValue)) {
						return false;
					}
				}
			}
			return true;
		}
		
		/* For String, Number, String, Pointer, Function */
		return this.script.equals(element.script);
	}
	
	/**
	 * <i>Script::equals(Object)</i><br />
	 * Compare the current script with the equivalent relationship of the incoming object.
	 * <br /><br />
	 * For <b>instance of Script</b>, equals() typed equals for identical simple types and
	 * deep recursion for composite types.<br />
	 * For others，equals() compares this.script with the incoming obj.
	 */
	@Override
	public boolean equals(java.lang.Object obj) {
		
		/** Undefined differs from one another */
		if (this.type == Type.Undefined) {
			return false;
		}
		
		/* null is considered as Script.Null */
		if (this == obj || obj == null && this.type == Type.Null) {
			return true;
		}
		
		/* Null is not allowed in the latter comparison */
		if (obj == null) {
			return false;
		}
		
		/* Comparisons between the same types of Script */
		if (obj instanceof Script) {
			/** Compare two Script elements */
			return this.equalsScript((Script)obj);
		}
		
		/* For obj = int, boolean, String, other Objects */
		return this.script.equals(obj);
	}
	
	@Override
	public int hashCode() {
		if (this.type == Type.Undefined) {
			return -1;
		}
		
		if (this.type == Type.Null) {
			return 0;
		}
		return this.script.hashCode();
	}
	/**********************************************************/
	
	
	/**********************************************************
	        Native String Application Program Interface
	 **********************************************************/
	
	/**
	 * <i>String::stringValue()</i><br />
	 * Returns the string entity of this Script element.
	 */
	public String stringValue() {
		
		//Check the validity of the definition of Script elements
		this.checkUndefined("String::stringValue()");
		
		//Check the validity of Script element types
		this.checkType("String::stringValue()", Type.String);
		
		return (String)this.script;
	}
	
	/**********************************************************/
	
	
	/**********************************************************
	        Native Number Application Program Interface
	 **********************************************************/
	
	/**
	 * <i>Number::intValue()</i><br />
	 * Returns the int entity of this Script element.
	 */
	public int intValue() {
		
		//Check the validity of the definition of Script elements
		this.checkUndefined("Number::intValue()");
		
		//Check the validity of Script element types
		this.checkType("Number::intValue()", Type.Number);
		
		return (Integer)this.script;
	}
	
	/**********************************************************/
	
	
	/**********************************************************
	        Native Boolean Application Program Interface
	 **********************************************************/
	
	/**
	 * <i>Boolean::booleanValue()</i><br />
	 * Returns the boolean entity of this Script element.
	 */
	public boolean booleanValue() {
		
		//Check the validity of the definition of Script elements
		this.checkUndefined("Boolean::booleanValue()");
		
		//Check the validity of Script element types
		this.checkType("Boolean::booleanValue()", Type.Boolean);
		
		return (Boolean)this.script;
	}
	
	/**********************************************************/
	
	
	/**********************************************************
	       Native Function Application Program Interface
	 **********************************************************/
	
	/**
	 * <i>Function::run()</i><br />
	 * The Core Method of Script Function.<br /><br />
	 * When constructing a function, we need to override the run() method of a Script
	 * subclass, and then pass the instance of the subclass to the Script.function().
	 * <br /><br />Just like the following code:
	 * <pre><code>
	 *     Script function = Script.Function(new Script() {
	 *         public void run(Script res) {
	 *             System.out.println(res);
	 *         }
	 *     });
	 * </code></pre>
	 * We can use the following method to invoke the function:
	 * <pre><code>
	 *     function.run(Script.ListObject("test", Script,True));
	 * </code></pre>
	 * Of course, functions can also be used as Script elements to participate in
	 * the construction of complex data structures and the transfer of parameters.
	 */
	public void run(Script res) {

		//Check the validity of the definition of Script elements
		this.checkUndefined("Function::run()");
		
		//Check the validity of Script element types
		this.checkType("Function::run()", Type.Function);
		
		/** Relocation, call up function */
		Script nativeFunction = (Script)this.script;
		nativeFunction.run(res);
	}
	
	/**********************************************************/
	
	
	/**********************************************************
	        Native Pointer Application Program Interface
	 **********************************************************/
	
	public Object Access() {

		//Check the validity of the definition of Script elements
		this.checkUndefined("Pointer::Access()");
		
		//Check the validity of Script element types
		this.checkType("Pointer::Access()", Type.Pointer);
		
		/** Return pointer object */
		return this.script;
	}
	
	/**********************************************************/
	
	/**
	 * <i>{Array, Object}::clean()</i>
	 * <br /><br />
	 * For <i>Array</i>, clean() removes all Undefined elements from the array, and the spaces are merged.
	 * <br />
	 * For <i>Object</i>, clean() removes all key-value pairs whose values are Undefined in the object.
	 */
	public void clean() {
		
		//Check the validity of the definition of Script elements
		this.checkUndefined("{Array, Object}::clean()");
		
		if (this.type != Type.Array && this.type != Type.Object) {//Native Code Acceleration
			throw new RuntimeException(
					"{Array, Object}::clean() method cannot be invoked on non-{Array, Object}"
			);	
		}
		
		/** Relocation by coincidence method */
		if (this.type == Type.Array) {
			this.Array$clean();
		} else if (this.type == Type.Object) {
			this.Object$clean();
		}
	}
	
	/**
	 * <i>{Array, Object}::clear()</i>
	 * <br /><br />
	 * Clear all mapping relationships or collection elements.
	 */
	public void clear() {
		
		//Check the validity of the definition of Script elements
		this.checkUndefined("{Array, Object}::clear()");
		
		if (this.type != Type.Array && this.type != Type.Object) {//Native Code Acceleration
			throw new RuntimeException(
					"{Array, Object}::clear() method cannot be invoked on non-{Array, Object}"
			);	
		}
		
		/** Relocation by coincidence method */
		if (this.type == Type.Array) {
			this.Array$clear();
		} else if (this.type == Type.Object) {
			this.Object$clear();
		}
	}
	
	/**********************************************************
	         Native Array Application Program Interface
	 **********************************************************/
	
	/**
	 * <i>Array::length()</i><br />
	 * Get the length of an array.
	 * <br /><br />
	 * Warning: <i>This method is thread-safe</i>.
	 */
	@SuppressWarnings("unchecked")
	public final int length() {
		
		//Check the validity of the definition of Script elements
		this.checkUndefined("Array::length()");
		
		//Check the validity of Script element types
		this.checkType("Array::length()", Type.Array);
		
		/**
		 * Array:: int: { get Array Size () }
		 * Get the length of the array
		 */
		return ((java.util.ArrayList<Script>)this.script).size();
	}
	
	/**
	 * <i>Array::append(Object)</i><br />
	 * Append a Script element to the end of the array.
	 * <br /><br />
	 * Warning: <i>This method is thread-safe</i>.<br />
	 * <i>The element added by this method is a copy of the incoming object</i>.
	 */
	public final int append(Object object) {
		
		//Check the validity of the definition of Script elements
		this.checkUndefined("Array::append(Object)");
		
		//Check the validity of Script element types
		this.checkType("Array::append(Object)", Type.Array);
		
		/**
		 * Array:: int: { append element (Script) }
		 * Add an element to the end of the array
		 */
		@SuppressWarnings("unchecked")
		java.util.ArrayList<Script> nativeArray = (java.util.ArrayList<Script>)this.script;
		nativeArray.add(new Script(object));
		return nativeArray.size();
	}
	
	/**
	 * <i>Array::index(int)</i><br />
	 * Gets the element (directly referenced) of the specified subscript in the array.
	 * <br /><br />
	 * Warning: <i>This method is thread-safe</i>.<br />
	 * <i>Negative index can be addressable in reverse order</i>.
	 */
	public final Script index(int index) {
		
		//Check the validity of the definition of Script elements
		this.checkUndefined("Array::index(int)");
		
		//Check the validity of Script element types
		this.checkType("Array::index(int)", Type.Array);
		
		/**
		 * Array:: Script: { get Array member (int) }
		 * Use indexs to get Array members
		 */
		@SuppressWarnings("unchecked")
		java.util.ArrayList<Script> nativeArray = (java.util.ArrayList<Script>)this.script;
		index = index < 0 ? index + nativeArray.size() : index;
		
		if (index < 0 || index >= nativeArray.size()) {
			throw new ArrayIndexOutOfBoundsException(
					"index: " + index + ", the range of the Array is [0, " + (nativeArray.size() - 1) + "]"
			);
		}
		return nativeArray.get(index);
	}
	
	/**
	 * <i>Array::remove(int)</i><br />
	 * Removing elements at specified positions in an array causes elements
	 * to merge forward after removal, resulting in lower efficiency.
	 * <br /><br />
	 * Warning: <i>This method is thread-safe</i>.<br />
	 * <i>Negative index can be addressable in reverse order</i>.
	 */
	public final int remove(int index) {
		
		//Check the validity of the definition of Script elements
		this.checkUndefined("Array::remove(int)");
		
		//Check the validity of Script element types
		this.checkType("Array::remove(int)", Type.Array);
		
		/**
		 * Array:: int: { remove Array member (int) }
		 * Use subscripts to remove array elements, which results in merging
		 */
		@SuppressWarnings("unchecked")
		java.util.ArrayList<Script> nativeArray = (java.util.ArrayList<Script>)this.script;
		index = index < 0 ? index + nativeArray.size() : index;
		
		if (index < 0 || index >= nativeArray.size()) {
			throw new ArrayIndexOutOfBoundsException(
					"index: " + index + ", the range of the Array is [0, " + (nativeArray.size() - 1) + "]"
			);
		}
		nativeArray.remove(index);
		return nativeArray.size();
	}
	
	/**
	 * <i>Array::clean()</i><br />
	 * Delete the Undefined element in Array and the spaces will be merged.
	 */
	private final void Array$clean() {
		/** Type checking is done at the converter */
		
		/**
		 * Array:: void: { remove Undefined item () }
		 * Delete the Undefined element in Array and the spaces will be merged.
		 */
		@SuppressWarnings("unchecked")
		java.util.ArrayList<Script> nativeArray = (java.util.ArrayList<Script>)this.script;
		
		for (int index = 0; index < nativeArray.size(); index++) {
			if (nativeArray.get(index).type == Type.Undefined) {
				nativeArray.remove(index);
				index--;
			}
		}
	}
	
	/**
	 * <i>Array::clear()</i><br />
	 * Clear this Array.
	 */
	private final void Array$clear() {
		/** Type checking is done at the converter */
		
		/**
		 * Array:: void: { Clear this array () }
		 * Clear this array.
		 */
		@SuppressWarnings("unchecked")
		java.util.ArrayList<Script> nativeArray = (java.util.ArrayList<Script>)this.script;
		nativeArray.clear();
	}
	/**********************************************************/
	
	
	/**********************************************************
	        Native Object Application Program Interface
	 **********************************************************/
	
	/** Check the validity of key */
	private static boolean checkKeyType(Script key) {
		/** Recursive Endpoint: Checking Illegal Types */
		if (key.type == Type.Undefined || key.type == Type.Function || key.type == Type.Pointer) {
			return false;
		}
		if (key.type == Type.Array) {
			@SuppressWarnings("unchecked")
			java.util.ArrayList<Script> nativeArray = (java.util.ArrayList<Script>)key.script;
			for (Script it : nativeArray) {
				if (!Script.checkKeyType(it)) return false;
			}
		}
		if (key.type == Type.Object) {
			@SuppressWarnings("unchecked")
			java.util.Map<Script, Script> nativeObject = (java.util.Map<Script, Script>)key.script;
			java.util.Iterator<Map.Entry<Script, Script>> nativeObjectIterator = nativeObject.entrySet().iterator();
			while (nativeObjectIterator.hasNext()) {
				if (!Script.checkKeyType(nativeObjectIterator.next().getValue())) return false;
			}
		}
		return true;
	}
	
	/**
	 * <i>Object::mem(Script)</i><br />
	 * Addressing a value in a Script object with the specified key.
	 * <br /><br />
	 * Only the following types of Script elements can be used as Script keys:<br />
	 * <i> { Null, Number, String, Boolean, Array(<u>Except: Undefined, Pointer, Function)</u>
	 * , Object(<u>Except: Undefined, Pointer, Function)</u> } </i>
	 */
	public Script mem(Object key) {

		//Check the validity of the definition of Script elements
		this.checkUndefined("Object::mem(Object)");
		
		//Check the validity of Script element types
		this.checkType("Object::mem(Object)", Type.Object);
		
		//Exception Detection
		Script objectKey = new Script(key);
		if (!checkKeyType(objectKey)) {
			throw new RuntimeException(
					"Key Script " + objectKey + " cannot be an Object key"
			);
		}
		
		/**
		 * Object:: Script: { Accessing Elements in Object (Script key) }
		 * Accessing Elements in Object by using Script key
		 */
		@SuppressWarnings("unchecked")
		java.util.Map<Script, Script> nativeObject = (java.util.Map<Script, Script>)this.script;
		
		if (!nativeObject.containsKey(objectKey)) {
			nativeObject.put(objectKey, Script.Undefined());
		}
		
		return nativeObject.get(objectKey);
	}
	
	/**
	 * <i>Object::keySet()</i><br />
	 * Gets the set of keys for all non-Undefined values of a Script object.
	 * <br /><br />
	 * Warning: <i>This method actively clears invalid key-value pairs in the mapping
	 * relationship and traverses each call. Its called should be reduced to save
	 * time and cost</i>.
	 */
	@SuppressWarnings("unchecked")
	public java.util.Set<Script> keySet() {

		//Check the validity of the definition of Script elements
		this.checkUndefined("Object::keySet()");
		
		//Check the validity of Script element types
		this.checkType("Object::keySet()", Type.Object);
		
		//Clear all key-value pairs whose values are Undefined
		this.Object$clean();
		
		/**
		 * Object:: Set<Script>: { Get a collection of all the keys of Script::Object () }
		 * Get a collection of all the keys of Script::Object
		 */
		java.util.Map<Script, Script> nativeObject = (java.util.Map<Script, Script>)this.script;
		return new java.util.HashSet<Script>(nativeObject.keySet());
	}
	
	/**
	 * <i>Object::clean()</i><br />
	 * Clear all invalid key-value pairs in this Object.
	 */
	private void Object$clean() {
		/** Type checking is done at the converter */
		
		/**
		 * Object:: void: { Clearing invalid key-value pairs () }
		 * Clear all key-value pairs whose values are Undefined
		 */
		@SuppressWarnings("unchecked")
		java.util.Map<Script, Script> nativeObject = (java.util.Map<Script, Script>)this.script;
		/**
		 * Get Entry iterator of Map
		 * Native and Efficient Iteration Scheme
		 */
		java.util.Iterator<java.util.Map.Entry<Script, Script>> nativeObjectIterator = nativeObject.entrySet().iterator();

		/** Recording a set of invalid keys */
		java.util.Set<Script> undefinedKeys = new java.util.HashSet<Script>();
		
		/** Iterative process for retrieving invalid keys */
		java.util.Map.Entry<Script, Script> nativeEntry;
		while (nativeObjectIterator.hasNext()) {
			nativeEntry = nativeObjectIterator.next();
			if (nativeEntry.getValue().type == Type.Undefined) {
				undefinedKeys.add(nativeEntry.getKey());
			}
		}
		
		/** Delete invalid keys */
		for (Script deletedKey : undefinedKeys) {
			nativeObject.remove(deletedKey);
		}
	}
	
	/**
	 * <i>Object::clear()</i><br />
	 * Clear all key-value pairs in this Object.
	 */
	private void Object$clear() {
		/** Type checking is done at the converter */
		
		/**
		 * Object:: void: { Remove all key-value pairs () }
		 * Remove all key-value pairs from the Object
		 */
		@SuppressWarnings("unchecked")
		java.util.Map<Script, Script> nativeObject = (java.util.Map<Script, Script>)this.script;
		nativeObject.clear();
	}
	
	/**********************************************************/
}
