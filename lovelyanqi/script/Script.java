/*
 * CopyRight (c) 2018, 2019, LovelyAnQi
 * Script: Structured Type Unification Support
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

/**
 * <b> LovelyAnQi.Script Framework <i>Script CopyRight V2.3</i></b>
 * <br /><br />
 * Class <code>Script</code> is the core unit of the <b><i>LovelyAnQi Script</i></b> framework.<br />
 * <code>Script</code> is committed to unify <i>Java basic types</i> and <i>Java functions</i>.<br />
 * The Script framework is dedicated to improving the linkages of all data types.<br />
 * Script also provides Java with more flexible pointer types.
 * <br /><br />
 * We can define a Script object in a very flexible way:
 * <pre>
 *     Script s_p = new Script();           //s_i.typeof() equals <b>Undefined</b>  s_p == Script::Undefined
 *     Script s_i = new Script(null);       //s_i.typeof() equals <b>Null</b>       s_i equals null
 *     Script s_n = new Script(0);          //s_n.typeof() equals <b>Number</b>     s_n equals 0
 *     Script s_b = new Script(true);       //s_b.typeof() equals <b>Boolean</b>    s_b equals true
 *     Script s_s = new Script("string");   //s_s.typeof() equals <b>String</b>     s_s equals "string"
 * </pre>
 * 
 * You can also use the above types to combine more advanced types:
 * <pre>
 *     Script array = Script.Array(0, 1, 2, true, "Script");
 *     //array.typeof() equals <b>Array</b>, array.toString() equals "[0, 1, 2, true, "Script"]"
 *     
 *     Script object = Script.ListObject("attr0", true, "attr1", "-1");
 *     //object.typeof() equals <b>Object</b>, object.toString() equals "{attr0: true, attr1: "-1"}"
 * </pre>
 * 
 * Of course, combined high-level types can still combine new script elements:
 * <pre>
 *     Script array = Script.Array(0, 1);
 *     Script binArray = Script.Array(array, array);
 *     //binArray.typeof() equals <b>Array</b>, binArray.toString() equals "[[0, 1], [0, 1]]"
 * </pre>
 * 
 * You can even use Script to build anonymous functions:
 * <pre>
 *     Script function = Script.Function((Object... objects) -> {
 *         System.out.println("Hello World!");
 *         return null;
 *     });
 * </pre>
 * 
 * You can also use script to build pointers:
 * <pre>
 *     Character character = Character.valueOf('C');
 *     Script pointer = new Script(character);
 *     //poiner.typeof() equals <b>Pointer</b>, pointer.toString() equals "pointer-> { C }"
 * </pre>
 * There is no doubt that pointers and anonymous functions can also be used to construct
 * the above advanced types.
 * <br /><br />
 * <i>It is noteworthy that Script is not directly used to express data structures and provide
 * rich implementations for them. Script is mainly used to provide an extensible computer
 * language framework as its basic class.<i>
 * <br /><br />
 * <b><i>Script CopyRight V2.3  @author LovelyAnQi 2019.07.29</i></b>
 */
public final class Script implements Functional/*, java.lang.Iterable<Script>*/ {
	/**
	 * <b>LovelyAnQi.Script class <i>Script</i> Version</b>
	 * <br />
	 * The Script Framework version and the underlying firmware
	 * version are independent of each other.
	 * <br />
	 * The Script Framework will select the specified stable
	 * firmware to launch the new version.
	 */
	public static final String CopyRight = "LovelyAnQi.Script.Script [版本 V2.3 2019.07.29]";
	
	/** <b>Type filter</b><br /> */
	private enum Type {
		Undefined, Null, String, Number, Boolean, Function, Pointer, Array, Object
	}
	
	public static interface VOID_Functional {
		//TODO
		public void run(java.lang.Object... objects);
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
	private java.lang.Object script;
	
	/**
	 * <b>Script Element Type</b><br />
	 * A script element can only be one of the nine types identified.
	 */
	private Type type;
	
	/**
	 * <b>Public field: <i>False</i></b><br />
	 * Common Constants Defined by Script = <b>Script::Boolean(false)</b><br />
	 * This public field cannot be assigned, and references to this public field
	 * do not create new objects.
	 */
	public static final Script False = Script.Boolean(false).lock();
	
	/**
	 * <b>Public field: <i>Null</i></b><br />
	 * Common Constants Defined by Script = <b>Script::Null()</b><br />
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
	 * <b>Private field: <i>NullFunction</i></b><br />
	 * The system's built-in reserved function, whose return value is null,
	 * has no practical significance.
	 */
	private static final Functional NullFunction = new Functional() {
		
		@Override
		public Script run(java.lang.Object... parameters) {
			return null;
		}
		
		@Override
		public java.lang.String toString() {
			return "System Built-In Empty Function";
		}
	};
	
	/**
	 * <b><i>Constant Lock</i></b><br />
	 * The return value of a constant lock is the element itself, and a constant
	 * lock is applied to the element.
	 * <br /><br />
	 * The following types of elements become constants, that is, they cannot be
	 * changed:<br />
	 * <i> { Undefined, Null, String, Number, Boolean, Pointer, Function }</i>
	 * <br /><br />
	 * The types of elements of the following types will be locked, that is, their
	 * types cannot be changed:<br />
	 * <i> { Array, Object } </i>
	 * <br /><br />
	 * Constant locks ensure that variables cannot be assigned or deleted.
	 */
	public final Script lock() {
		this.checkUndefined("lock()");
		
		this.isConstant = true;
		return this;
	}
	
	/**
	 * <b>Unlocking Constant</b><br />
	 * Unlock the constant lock of the element to restore the normal variable.
	 */
	public final Script unlock() {
		this.checkUndefined("unlock()");
		
		this.isConstant = false;
		return this;
	}
	
	
	
	
	/**********************************************************
	                      Constructor Set
	 **********************************************************/
	
	/**
	 * <b>Native Constructor</b><br />
	 * Private constructor, one-step initialization using specified initial state.
	 */
	private Script(java.lang.Object script, Type type) {
		super();							//Call Object.Object()
		
		/** Using the incoming parameter to construct a Script element */
		this.script = script;
		this.type = type;
		
		this.isConstant = false;			//Default = false
	}
	
	/**
	 * <b>Default Constructor</b><br />
	 * The no parameters constructor of Script, which returns an instance of Script of Undefined type.
	 * <br />
	 * Script() initializes a Script element of <b>Undefined</b> type.
	 * <pre>
	 *     Script s_p = new Script();       //s_p.typeof() equals "Undefined"
	 * </pre>
	 * An Undefined variable <i>is allowed to be assigned or deleted</i>, but no other operation
	 * is allowed.
	 */
	public Script() {
		this(null, Type.Undefined);		//Construct An Undefined Script Element
	}
	
	/**
	 * <b>Type Initialization Constructor</b><br />
	 * <i>Type initialization constructors can infer finite types and generate Script
	 * instances of the corresponding types.</i>
	 * <br /><br />
	 * Initialization type inference supports the following basic types:
	 * <br /><code>{ null, Integer, Boolean, String }</code><br />
	 * Type inference can also infer the following array types in the form of initialization lists:
	 * <br /><code>{ int[], boolean[], String[] }</code>
	 * <br /><br />
	 * <i>In particular, we recommend using the type generator of the specified type instead of the
	 * constructor of type inference, so that the generated type must be reliable and has no inferred cost.</i>
	 */
	public Script(java.lang.Object object) {
		this(null, Type.Undefined);		//Default initialization
		
		/**
		 * Directional instantiation: Null
		 * Initialize a type of Script element for Null.
		 */
		if (object == null) {
			this.type = Type.Null; return;	//Initialization complete
		}
		
		/** Equivalent to cloning a new script element */
		if (object instanceof Script) {	
			/** Cloning a copy of the current instance using the target script instance */
			this.Clone((Script)object); return;
			
			/** Building a new script element with a given Java member */
		} else {							//Type Inference Branch
			
			/** Processing target data of type { Integer, Boolean, String } */
			if (object instanceof java.lang.Integer || object instanceof java.lang.Boolean
					|| object instanceof java.lang.String) {
				this.let(object);
				
				/* Processing target data of type int[] */
			} else if (object instanceof int[]) {
				this.type = Type.Array;		//Type Relocation
				int[] nativeNumberArray = (int[])object;
				java.util.Vector<Script> nativeArray = new java.util.Vector<Script>();
				
				for (int index = 0; index < nativeNumberArray.length; index++) {
					/** Native acceleration */
					nativeArray.add(Script.Number(nativeNumberArray[index]));
				}
				this.script = nativeArray;
				
				/* Processing target data of type boolean[] */
			} else if (object instanceof boolean[]) {
				this.type = Type.Array;		//Type Relocation
				boolean[] nativeBooleanArray = (boolean[])object;
				java.util.Vector<Script> nativeArray = new java.util.Vector<Script>();
				
				for (int index = 0; index < nativeBooleanArray.length; index++) {
					/** Native acceleration */
					nativeArray.add(Script.Boolean(nativeBooleanArray[index]));
				}
				this.script = nativeArray;
				
				/* Processing target data of type String[] */
			} else if (object instanceof java.lang.String[]) {
				this.type = Type.Array;		//Type Relocation
				java.lang.String[] nativeStringArray = (java.lang.String[])object;
				java.util.Vector<Script> nativeArray = new java.util.Vector<Script>();
				
				for (int index = 0; index < nativeStringArray.length; index++) {
					/** Native acceleration */
					nativeArray.add(Script.String(nativeStringArray[index]));
				}
				this.script = nativeArray;
				
				/** Other types are converted to script pointers */
			} else {
				this.type = Type.Pointer;	//Type relocation
				this.script = object;
			}
		}
	}
	
	/**********************************************************/
	
	
	
	
	/**********************************************************
	          Primitive Type Generation Component Set
	 **********************************************************/
	
	/**
	 * <b>Script Type Constructor: Undefined</b><br />
	 * Undefined() is an Undefined type generator, which can reliably generate
	 * a Script instance of Undefined type.
	 */
	public static final Script Undefined() {
		
		/** Native acceleration */
		return new Script(null, Type.Undefined);
	}
	
	/**
	 * <b>Script Type Constructor: Null</b><br />
	 * Null() is a Null type generator, which can reliably generate a Script
	 * instance of Null type.
	 */
	public static final Script Null() {

		/** Native acceleration */
		return new Script(null, Type.Null);
	}
	
	/**
	 * <b>Script Type Constructor: String</b><br />
	 * String() is a String type generator, which can reliably generate a Script
	 * instance of String type.
	 * <br /><br />
	 * <i>String() requires a string as a parameter to be used as the seed
	 * of the generated Script instance. If null is passed in, String() treats
	 * it as an empty string.</i>
	 */
	public static final Script String(java.lang.String string) {
		
		java.lang.String nativeString = (string == null ? "" : string);
		/** Native acceleration */
		return new Script(nativeString, Type.String);
	}
	
	/**
	 * <b>Script Type Constructor: Number</b><br />
	 * Number() is a Number type generator, which can reliably generate a Script
	 * instance of Number type.
	 * <br /><br />
	 * <i>Number() requires an integer as a parameter to be used as the seed of
	 * the generated Script instance. If null is passed in, Number() treats it as 0.</i>
	 */
	public static final Script Number(java.lang.Integer number) {
		
		java.lang.Integer nativeNumber = (number == null ? java.lang.Integer.valueOf(0) : number);
		/** Native acceleration */
		return new Script(nativeNumber, Type.Number);
	}
	
	/**
	 * <b>Script Type Constructor: Boolean</b><br />
	 * Boolean() is a Boolean type generator, which can reliably generate a Script
	 * instance of Boolean type.
	 * <br /><br />
	 * <i>Boolean() requires a truth value as a parameter to be used as the seed of the
	 * generated Script instance. If null is passed in, Boolean() treats it as false.</i>
	 */
	public static final Script Boolean(java.lang.Boolean truth) {
		
		java.lang.Boolean nativeBoolean = (truth == null ? java.lang.Boolean.FALSE : truth);
		/** Native acceleration */
		return new Script(nativeBoolean, Type.Boolean);
	}
	
	/**
	 * <b>Script Type Constructor: Function</b><br />
	 * Function() is a Function type generator, which can reliably generate a Script
	 * instance of Function type.
	 * <br /><br />
	 * <i>Function() requires an instance of a class that implements the Functional
	 * interface to be used as a parameter to seed the generated Script instance.
	 * If null is passed in, Function() creates an empty function.</i>
	 * <br /><br />
	 * The incoming function will be rebuilt by calling the default constructor of its class.
	 * The rebuilding may fail and "Runtime Exception" will be thrown when it fails.
	 */
	public static final Script Function(Functional functional) {
		
		Functional nativeFunction = (functional == null ? Script.NullFunction : functional);
		
		/** Function Reconstruction: Generate a new instance of the class in which the incoming instance resides */
		try {
			java.lang.reflect.Constructor<? extends Functional> nativeConstructor =
					nativeFunction.getClass().getDeclaredConstructor();
			nativeConstructor.setAccessible(true); nativeFunction = nativeConstructor.newInstance();
		} catch (java.lang.Exception e) {
			throw new java.lang.RuntimeException(
					"Unable to reconstruct functions, Unresolved errors"
			);
		}
		
		/** Native acceleration */
		return new Script(nativeFunction, Type.Function);
	}
	
	/**
	 * <b>Script Type Constructor: Pointer</b><br />
	 * Pointer() is a Pointer type generator, which can reliably generate a Script
	 * instance of Pointer type.
	 * <br /><br />
	 * <i>Pointer() accepts instances of any class as parameters and uses them as seeds of
	 * generated Script instances. If null is passed in, Pointer() treats them as Script.Undefined().</i>
	 */
	public static final Script Pointer(java.lang.Object object) {
		
		java.lang.Object nativePointer = (object == null ? Script.Undefined() : object);
		/** Native acceleration */
		return new Script(nativePointer, Type.Pointer);
	}
	
	/**
	 * <b>Script Type Constructor: Array</b><br />
	 * Array() is an Array type generator, which can reliably generate a Script
	 * instance of Array type.
	 * <br /><br />
	 * <i>Array() accepts an infinite number of instances of any class as parameters and uses
	 * them as seeds of generated Script instances. Array() will use constructors that can infer
	 * types to sequentially generate their new Script instances one by one, and add new
	 * instances to the generated array.<br />
	 * If null or no parameters are passed in, Array() will create an empty array.<i>
	 */
	public static final Script Array(java.lang.Object... objects) {
		
		java.util.Vector<Script> nativeArray = new java.util.Vector<Script>();
		
		/** Array generation: Adding new instances of type inference in sequence */
		if (objects != null && objects.length != 0) {
			for (int index = 0; index < objects.length; ++index) {
				nativeArray.add(new Script(objects[index]));
			}
		}
		
		/** Native acceleration */
		return new Script(nativeArray, Type.Array);
	}
	
	/**
	 * <b>Script Type Constructor: Object</b><br />
	 * Object() is an Object type generator, which can reliably generate a Script
	 * instance of Object type.
	 * <br /><br />
	 * <i>Object() does not accept any parameters, and Object() returns an empty
	 * Script instance of Object type.</i>
	 */
	public static final Script Object() {
		
		java.util.Map<Script, Script> nativeObject = new java.util.HashMap<Script, Script>();
		/** Native acceleration */
		return new Script(nativeObject, Type.Object);
	}
	
	/**********************************************************/
	
	
	
	
	/**********************************************************
	             Convenient Construction of Script
	 **********************************************************/
	
	/**
	 * <b>Script Convenient Construction: Object</b><br />
	 * ListObject() allows you to build an Object with even instances, and ListObject()
	 * picks the first as the key and the second as the value of each pair of instances.
	 * <br /><br />
	 * <i>ListObject() infers and instantiates key and value into script instances, when
	 * null or no parameters are passed in, ListObject() returns an empty Script instance
	 * of Object type.</i>
	 * <br /><br />
	 * <i>Not all parameters can be used as key types, such as Undefined, Function, Pointer.
	 * In other words, the types that can be used as keys must be serializable.</i>
	 */
	public static final Script ListObject(java.lang.Object... objects) {
		
		if (objects == null || objects.length == 0) {
			return Script.Object();
		}
		
		if (objects.length % 2 != 0) {
			throw new IllegalArgumentException(
					"ListObject() requires even numbers of parameters"
			);
		}
		
		Script key;							//Temporarily retain key references
		java.util.Map<Script, Script> nativeObject = new java.util.HashMap<Script, Script>();
		for (int i = 0; i < objects.length; i += 2) {
			if (!checkKeyType(key = new Script(objects[i]))) {
				throw new java.lang.RuntimeException(
						"Key Script " + key + " cannot be an Object key"
				);
			}
			nativeObject.put(key, new Script(objects[i + 1]));
		}
		
		/** Native acceleration */
		return new Script(nativeObject, Type.Object);
	}
	
	/**
	 * <b>Script Convenient Construction: Object</b><br />
	 * TemplateObject() receives a series of parameters and generates an Object with
	 * these parameters as keys, whose corresponding values are Null.
	 * <br /><br />
	 * <i>Not all parameters can be used as key types, such as Undefined, Function, Pointer.
	 * In other words, the types that can be used as keys must be serializable.</i>
	 */
	public static final Script TemplateObject(java.lang.Object... keys) {
		
		if (keys == null || keys.length == 0) {
			return Script.Object();
		}
		
		Script key;							//Temporarily retain key references
		java.util.Map<Script, Script> nativeObject = new java.util.HashMap<Script, Script>();
		for (int i = 0; i < keys.length; ++i) {
			if (!checkKeyType(key = new Script(keys[i]))) {
				throw new java.lang.RuntimeException(
						"Key Script " + key + " cannot be an Object key"
				);
			}
			nativeObject.put(key, Script.Null());
		}
		
		/** Native acceleration */
		return new Script(nativeObject, Type.Object);
	}
	/**********************************************************/
	
	
	
	
	/**********************************************************
	                  Script Common Method Set
	 **********************************************************/
	
	/**
	 * <b>Script Common Method: checkConstant(String caller)</b><br />
	 * checkConstant() checks whether the current variable is a constant,
	 * and if so, checkConstant() throws RuntimeException.
	 */
	private final void checkConstant(java.lang.String caller) {
		if (this.isConstant()) {
			throw new java.lang.RuntimeException(
					"IllegalFunctionInvocationException\n\t" +
					caller + " method cannot be invoked on a constant"
			);
		}
	}
	
	/**
	 * <b>Script Common Method: checkUndefined(String caller)</b><br />
	 * checkUndefined() checks whether the current variable is an Undefined variable,
	 * and if so, checkUndefined() throws RuntimeException.
	 */
	private final void checkUndefined(java.lang.String caller) {
		if (this.type == Type.Undefined) {
			throw new java.lang.RuntimeException(
					"IllegalFunctionInvocationException\n\t" +
					caller + " method cannot be invoked on an Undefined variable"
			);
		}
	}
	
	/**
	 * <b>Script Common Method: checkType(String caller, Type test)</b><br />
	 * checkType() is used to check whether it is legal to invoke a function of a
	 * certain type on the current Script variable (or constant).
	 */
	private final void checkType(java.lang.String caller, Type... test) {
		for (int it = 0; it < test.length; ++it) {
			if (this.type == test[it]) return;
		}
		throw new java.lang.RuntimeException(
				"IllegalFunctionInvocationException\n\t" +
				caller + " method cannot be invoked on " + this.type.toString() + " variable"
		);	
	}
	
	/**
	 * <b>Script Common Method: typeof()</b><br />
	 * Returns a string that refers to the current Script element type.
	 */
	public final java.lang.String typeof() {
		return this.type.toString();
	}
	
	/**
	 * <b>Script Common Method: typeof(String testType)</b><br />
	 * Test whether the type of the current Script element is equivalent
	 * to the incoming string.
	 * <br /><br />
	 * <i>The test process ignores case letters.</i>
	 */
	public final boolean typeof(java.lang.String testType) {
		return this.typeof().equalsIgnoreCase(testType);
	}
	
	/**
	 * <b>Script Common Method: isConstant()</b><br />
	 * Returns whether a Script instance is a constant.
	 */
	public final boolean isConstant() {
		return this.isConstant;
	}
	
	/**
	 * <b>Script Common Method: delete()</b><br />
	 * Remove all data from a Script instance and set its type to Undefined.
	 */
	public final void delete() {
		this.checkConstant("delete()");
		
		this.script = null;
		this.type = Type.Undefined;
	}
	
	/**
	 * <b>Script Common Method: del()</b><br />
	 * Relocation to this.delete().
	 */
	public final void del() {
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
	 *     Script thisClone = script.Clone();					//This method
	 *     Script consturctorClone = new Script(script);		//Using constructive methods
	 *     Script relocationClone = new Script().Clone(script);	//Use relocation
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
		this.checkConstant("Clone(Script)");
		
		if (source == null) {
			return this.let(null);
		}
		
		/* Types that must be copied and reset */
		if (source.type == Type.Function) {
			Script nativeFunction = (Script)source.script;
			try {
				java.lang.reflect.Constructor<? extends Script> nativeConstructor = nativeFunction.getClass().getDeclaredConstructor();
				nativeConstructor.setAccessible(true);
				this.script = nativeConstructor.newInstance();
			} catch (Exception e) {
				throw new RuntimeException(
						"Unable to reconstruct functions, unresolved errors"
				);
			}
			this.type = Type.Function;
		}
		
		/* Composite data types */
		else if (source.type == Type.Array) {		//Array parsing
			
			@SuppressWarnings("unchecked")
			//Get the data structure itself of the target Array
			java.util.Vector<Script> externArray = (java.util.Vector<Script>)source.script;
			java.util.Iterator<Script> externArrayIterator = externArray.iterator();	//Obtaining iterators
			java.util.Vector<Script> nativeArray = new java.util.Vector<Script>();
			
			/* Iterate the original Array sequentially */
			while (externArrayIterator.hasNext()) {
				/**
				 * Calling new Script(Script) directly is more efficient than calling script.Clone()
				 */
				nativeArray.add(new Script(externArrayIterator.next()));
			}
			
			this.script = nativeArray;
			this.type = Type.Array;					//Type relocation
		}
		
		/* Composite data types */
		else if (source.type == Type.Object) {		//Object parsing
			
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
					continue;						//Undefined type key pairs will not be copied
				}
				
				/**
				 * Calling new Script(Script) directly is more efficient than calling script.Clone()
				 */
				nativeObject.put(new Script(nativeEntry.getKey()), new Script(nativeEntry.getValue()));
			}

			this.script = nativeObject;
			this.type = Type.Object;				//Type relocation
		} else {
			/**
			 * Type as:	( those.script is constant )
			 * { Undefined, Null, String, Number, Boolean, Pointer }
			 */
			this.script = source.script;
			this.type = source.type;
		}
		
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
			return this;						//Initialization complete
		}
		
		/** Equivalent to cloning a new script element */
		if (object instanceof Script) {			//Clone structure
			/** Cloning a copy of the current instance using the target script instance */
			this.Clone((Script)object);			//Call the cloning method
			return this;						//Initialization complete
			
			/** Building a new script element with a given Java member */
		} else {
			
			/** let() type conversion when accepting assignments of only { int, boolean, String } */
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
	private final java.util.Vector<Byte> passbytes(java.util.Vector<Byte> bytes) {
		
		/** Serialization type checking */
		if (this.type == Type.Pointer || this.type == Type.Function) {
			throw new RuntimeException(
					"Unsupported Serialization Type: " + this.typeof() + 
					"\tDetails: " + this.toString()
			);
		}
		
		/** Determine whether the array is initialized for the first time */
		if (bytes == null) {
			bytes = new java.util.Vector<Byte>();
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
			java.util.Vector<Script> nativeArray = (java.util.Vector<Script>)this.script;
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
		java.util.Vector<Byte> arrayBytes = this.passbytes(null);
		
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
				java.util.Vector<Script> nativeArray = (java.util.Vector<Script>)nativeScript.script;
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
			java.util.Vector<Script> nativeArray = (java.util.Vector<Script>)this.script;
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
			java.util.Vector<Script> nativeArray = (java.util.Vector<Script>)this.script;
			@SuppressWarnings("unchecked")
			java.util.Vector<Script> externArray = (java.util.Vector<Script>)element.script;
			
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
	 *         public void run(Script... res) {
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
	public Script run(Object... res) {

		//Check the validity of the definition of Script elements
		this.checkUndefined("Function::run()");
		
		//Check the validity of Script element types
		this.checkType("Function::run()", Type.Function);
		
		/** Relocation, call up function */
		Functional nativeFunction = (Functional)this.script;
		
		/**
		 * Parametric Standard Format Processing
		 */
		if (res == null || res.length == 0) {
			return nativeFunction.run();		//For invalid parameters, call the function with zero parameters
		}
		
		//Format Processing
		for (int it = 0; it < res.length; it++) {
			res[it] = new Script(res[it]);
		}
		
		return nativeFunction.run(res);
	}
	
	/**********************************************************/
	
	
	/**********************************************************
	        Native Pointer Application Program Interface
	 **********************************************************/
	
	/**
	 * <i>Pointer::access()</i><br />
	 * Gets the value pointed to by a pointer.<br />
	 */
	public Object access() {

		//Check the validity of the definition of Script elements
		this.checkUndefined("Pointer::Access()");
		
		//Check the validity of Script element types
		this.checkType("Pointer::Access()", Type.Pointer);
		
		/** Return pointer object */
		return this.script;
	}
	
	/**
	 * <i>Pointer::extract()</i><br />
	 * Unconditional cloning method on pointer.
	 * <br /><br />
	 * When the pointer points to a target that is a script element,
	 * Extract() copies all the features of the target element (excluding
	 * constant attributes, which must be extraordinary) to itself.
	 * <br /><br />
	 * For Basic Script Element: <i>{ Undefined, Null, String, Number, Boolean, Function, Pointer }</i>,
	 * Extract() is just a simple clone, which is equivalent to calling the Clone method on himself.
	 * <br />
	 * For Combination Script Element: <i>{ Array, Object }</i>,
	 * Extract() gets the element entity of the type, that is, the current pointer shares its
	 * internal data members with the target object.
	 */
	public Script extract() {

		//Check the validity of the definition of Script elements
		this.checkUndefined("Pointer::Extract()");
		
		//Check the validity of Script element types
		this.checkType("Pointer::Extract()", Type.Pointer);
		
		/**
		 * Forced cloning
		 */
		if (this.script instanceof Script) {
			Script nativeScript = (Script)this.script;
			this.script = nativeScript.script;
			this.type = nativeScript.type;
		}
		
		return this;
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
	 * Get the length of an array.The array of Script is implemented with variable length.
	 */
	@SuppressWarnings("unchecked")
	public final int length() {
		this.checkUndefined("Array::length()");
		this.checkType("Array::length()", Type.Array);
		
		return ((java.util.Vector<Script>)this.script).size();
	}
	
	/**
	 * <i>Array::checkIndex(int)</i><br />
	 * Array subscript crossover checker.
	 * The checker will operate on the absolute value subscripts and check the array
	 * crossing the bounds. The checker returns the subscripts when checking, and
	 * throws an exception instead.
	 */
	private int checkIndex(int get_index) {
		int _size = this.length();
		int calc_index = get_index < 0 ? get_index + _size : get_index;

		if (calc_index < 0 || calc_index >= _size) {
			throw new RuntimeException(
				"ArrayIndexOutOfBoundsException\n\t" +
				"index: " + get_index + ", the range of the Array is [0, " + (_size - 1) + "]"
			);
		}

		return calc_index;
	}
	
	/**
	 * <i>Array::append(Object)</i><br />
	 * Append a Script element to the end of the array.
	 * <br /><br />
	 * Warning: <i>This method is thread-safe</i>.<br />
	 * <i>The element added by this method is a copy of the incoming object</i>.
	 */
	public final int append(Object object) {
		this.checkUndefined("Array::append(Object)");
		this.checkType("Array::append(Object)", Type.Array);
		
		@SuppressWarnings("unchecked")
		java.util.Vector<Script> nativeArray = (java.util.Vector<Script>)this.script;
		nativeArray.add(new Script(object));
		return nativeArray.size();
	}
	
	/**
	 * <i>Array::index(int)</i><br />
	 * Gets the element (directly referenced) of the specified subscript in the array.
	 * <br />
	 * <i>Negative index can be addressable in reverse order</i>.
	 */
	public final Script index(int get_index) {
		this.checkUndefined("Array::index(int)");
		this.checkType("Array::index(int)", Type.Array);
		
		@SuppressWarnings("unchecked")
		java.util.Vector<Script> nativeArray = (java.util.Vector<Script>)this.script;
		int calc_index = this.checkIndex(get_index);
		return nativeArray.get(calc_index);
	}
	
	/**
	 * <i>Array::insert(int)</i><br />
	 * Insert an element into an array at an existing subscript.
	 * <br />
	 * <i>Negative index can be addressable in reverse order</i>.
	 */
	public final int insert(int insert_index, Object object) {
		this.checkUndefined("Array::insert(int)");
		this.checkType("Array::insert(int)", Type.Array);
		
		@SuppressWarnings("unchecked")
		java.util.Vector<Script> nativeArray = (java.util.Vector<Script>)this.script;
		int calc_index = this.checkIndex(insert_index);
		nativeArray.add(calc_index, new Script(object));
		return nativeArray.size();
	}
	
	public final Script set(int set_index, Object object) {
		//同步的置数函数
		@SuppressWarnings("unchecked")
		java.util.Vector<Script> nativeArray = (java.util.Vector<Script>)this.script;
		
		return nativeArray.set(set_index, new Script(object));
	}
	
	/**
	 * <i>Array::remove(int)</i><br />
	 * Removing elements at specified positions in an array causes elements
	 * to merge forward after removal, resulting in lower efficiency.
	 * <br /><br />
	 * Warning: <i>This method is thread-safe</i>.<br />
	 * <i>Negative index can be addressable in reverse order</i>.
	 */
	public final int remove(int remove_index) {
		this.checkUndefined("Array::remove(int)");
		this.checkType("Array::remove(int)", Type.Array);
		
		@SuppressWarnings("unchecked")
		java.util.Vector<Script> nativeArray = (java.util.Vector<Script>)this.script;
		int calc_index = this.checkIndex(remove_index);
		nativeArray.remove(calc_index);
		return nativeArray.size();
	}
	
	/**
	 * <i>Array::toArray()</i><br />
	 * Converting all elements of an Array into the form of an array.
	 */
	public final Script[] toArray() {

		//Check the validity of the definition of Script elements
		this.checkUndefined("Array::toArray()");
		
		//Check the validity of Script element types
		this.checkType("Array::toArray()", Type.Array);

		@SuppressWarnings("unchecked")
		java.util.Vector<Script> nativeArray = (java.util.Vector<Script>)this.script;
		
		Object[] objectArrays = nativeArray.toArray();
		Script[] scriptArrays = new Script[objectArrays.length];
		for (int index = 0; index < objectArrays.length; index++) {
			scriptArrays[index] = (Script)objectArrays[index];
		}
		return scriptArrays;
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
		java.util.Vector<Script> nativeArray = (java.util.Vector<Script>)this.script;
		
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
		java.util.Vector<Script> nativeArray = (java.util.Vector<Script>)this.script;
		nativeArray.clear();
	}
	
	/****************   Functional function    ****************/
	
	public final int push(Object object) {
		this.append(object);
		return this.length();
	}
	
	public final Script pop() {
		return this.pop(-1);
	}
	
	public final Script pop(int pop_index) {
		Script element = this.index(pop_index);
		this.remove(pop_index);
		return element;
	}
	
	public final Script top() {
		return this.index(-1);
	}
	
	public final int unshift(Object object) {
		int _size = this.length();
		if (_size == 0) {
			this.append(object);
		} else {
			this.insert(0, object);
		}
		return _size + 1;
	}
	
	public final Script shift() {
		Script element = this.index(0);
		this.remove(0);
		return element;
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
			java.util.Vector<Script> nativeArray = (java.util.Vector<Script>)key.script;
			for (Script it : nativeArray) {
				if (!Script.checkKeyType(it)) return false;
			}
		}
		if (key.type == Type.Object) {
			@SuppressWarnings("unchecked")
			java.util.Map<Script, Script> nativeObject = (java.util.Map<Script, Script>)key.script;
			java.util.Iterator<java.util.Map.Entry<Script, Script>> nativeObjectIterator = nativeObject.entrySet().iterator();
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
		this.checkUndefined("Object::mem(Object)");
		this.checkType("Object::mem(Object)", Type.Object);
		
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
	 * Gets all the keys in the current Script Object.(Significantly faster than staticKeySet())
	 * <br /><br />
	 * Warning: <i>This method relies on key-value mapping, and the operation of
	 * Object may cause the iterator to fail.</i><br />
	 * Specially, this method may return keys with the corresponding value of Undefined type.
	 */
	@SuppressWarnings("unchecked")
	public java.util.Set<Script> keySet() {
		this.checkUndefined("Object::keySet()");
		this.checkType("Object::keySet()", Type.Object);
		
		/**
		 * Object:: Set<Script>: { Get a collection of all the keys of Script::Object() }
		 * Get a collection of all the keys of Script::Object
		 */
		java.util.Map<Script, Script> nativeObject = (java.util.Map<Script, Script>)this.script;
		return nativeObject.keySet();
	}
	
	/**
	 * <i>Object::staticKeySet()</i><br />
	 * Gets the set of keys for all non-Undefined values of a Script object.
	 * <br /><br />
	 * Warning: <i>This method actively clears invalid key-value pairs in the mapping
	 * relationship and traverses each call. Its called should be reduced to save
	 * time and cost</i>.
	 */
	@SuppressWarnings("unchecked")
	public java.util.Set<Script> staticKeySet() {
		this.checkUndefined("Object::staticKeySet()");
		this.checkType("Object::staticKeySet()", Type.Object);
		
		//Clear all key-value pairs whose values are Undefined
		this.Object$clean();
		
		/**
		 * Object:: Set<Script>: { Get a collection of all the keys of Script::Object() }
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
	
	
	/**********************************************************
	          Native Asynchronous Operation Function
	 **********************************************************/
	
	public static final Promise setTimeout(final Script function, final int delayTime) {
		return new Promise(new Promise.PromiseInitialFunction() {
			
			@Override
			public void promiseInitialFunction(lovelyanqi.script.Promise.VOID_Function_OBJECT _onResolved,
					lovelyanqi.script.Promise.VOID_Function_OBJECT _onRejected) {
				new java.lang.Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							Thread.sleep(delayTime);
							_onResolved.run(function.run());
						} catch(Exception e) {
							_onRejected.run(e);
						}
						_onResolved.run(null);
					}
				}).start();
			}
		});
	}
	
	
	/**********************************************************/
}
