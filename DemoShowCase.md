In the following a language for describing software product line architectures will be demonstrated, using the example of a product line for collaboration systems. This language for expressing variability, which I call "VarDSL", was developed with the "Xtext Variability Toolset" and is also available in the SVN repository (http://dslvariantmanagement.googlecode.com/svn/trunk/demo/).
The main benefit of the VarDSL is, that you don't define only a single system with predefined requirements but rather a family of systems with different requirements. All systems in that family are defined in one model which makes it easier to modify, extend, remove or reuse elements from other systems. In addition a central model helps to keep all system architectures in a consistent state.

To demonstrate the VarDSL, we defined a sample model for a product line of collaboration systems. The following picture shows the feature model of this example product line.

![http://dslvariantmanagement.googlecode.com/svn/trunk/demo/trunk/dev_wiki/images/CollabFM_v3.jpg](http://dslvariantmanagement.googlecode.com/svn/trunk/demo/trunk/dev_wiki/images/CollabFM_v3.jpg)

Different collaboration systems can be derived from this sample model by simply selecting features from a feature model and without touching the code. In this way, we can derive several products with different functionality, such as instant messaging or forum functionality. Access management functionality can be added if there should be a need for more user roles. Additionally the data (audio, video and text) that will be sent through the collaboration system can be encrypted. This is particularly advisable if the messages are sent over an unsecured communication channel. A central document repository can be used to share knowledge among team members.

A collaboration system can be composed of components that have different types. Such components can be client-specific and define the user interface or they can be server-specific and define the business logic. The client-specific components are the so called “ui-components”, which are shown in the following listing:

```
system ui { 
	using collabSPL.logic;

	uicomponent InstantMessagingUI feature SynchronousConferencing {
		requires rDispatcher : IMessageDispatcher;
	}
	
	uicomponent ForumUI feature Forum {
		requires rDispatcher : IMessageDispatcher;
	}
	
	uicomponent MicroBloggingUI feature MicroBlogging {
		requires rDispatcher : IMessageDispatcher;
	}
}
```

The ui-component "InstantMessagingUI" is the user interface for the instant messenger, which implements the feature "synchronous conferencing". To make this user interface dependent on the "synchronous conferencing" feature, the ui-component is tagged with the expression "feature SynchronousConferencing". This expression guarantees, that only if the "SynchronousConferencing" feature is selected, the user interface for the instant messenger will be generated. In order to delegate the messages from the user interface to the according business logic components, a seperate component for message dispatching will be used. The following listing shows this component:

```
blcomponent MessageDispatcher{
    provides pUI :  IMessageDispatcher;  
    requires toMany rService : IMessageDispatcher;
    config {
        deployment  testEnvironment: boolean
        startup     timeOutMs:       numeric 
        instancedef minPoolSize:     numeric
        instancedef maxPoolSize:     numeric
    }
}
```

The business logic component "MessageDispatcher" is the proxy between the presentation layer and the business layer. To use the "MessageDispatcher" as a proxy, the provided port and the required port are of the same type ("IMessageDispatcher"). The type "IMessageDispatcher" is a generic interface for sending messages to different services. Due to the decoupling of the presentation layer from the business layer through the "MessageDispatcher", the existing ui-components do not have to be adapted, when services are extended or new ones are added. Another special feature is the additional keyword "toMany" which gives the ability to connect a required port to many provided ports. The required port "rService", which is the connector to the messaging service, needs the toMany-feature, because you may want to support more than one way to communicate with colleagues. Furthermore there are several configuration parameters defined for the "MessageDispatcher". The first keyword of the parameter declaration indicates the time phase in the component's lifecycle that the configuration parameter belongs to. Configuration parameters which are marked with the "deployment" keyword (i.e. "testEnvironment") are requested at deployment. For instance the configuration parameter "testEnvironment" will be requested to decide which environment the "MessageDispatcher" should be deployed in (the test or production environment).

To make the relationship between the components clear, the following uml component diagram gives an overview of the components overall structure.

![http://dslvariantmanagement.googlecode.com/svn/trunk/demo/trunk/dev_wiki/images/components_v1.jpg](http://dslvariantmanagement.googlecode.com/svn/trunk/demo/trunk/dev_wiki/images/components_v1.jpg)

The three blue components are representing the user interfaces for the several messengers. These components are connected through the "MessageDispatcher" with the related services. But so far no decision has been made on which component is to be picked and how to connect them together to derive a concrete product. In other words: the variation points are not bound to concrete variants. The variation points that are visible in the above figure are the required ports and the variants are represented by the provided ports. For instance one variation point of the component "InstantMessagingService" is the required port "audioCon". The variants which can be bound to the variation point are the provided ports of "VOIPConnection" and "TelephoneConnection". In this way it is possible to choose between different implementations (i.e. audio connections).

In order to build a concrete collaboration system the desired components must be chosen and wired together. The wiring will be done in a "deliverablesystem"-block which is presented in the following listing.

```
deliverablesystem collabsys feature SynchronousConferencing {
	instance imUI : InstantMessagingUI; 
	
	instance imDispatcher : MessageDispatcher	
	config {
	    minPoolSize = 100
	    maxPoolSize = 200
	};
	instance imService : InstantMessagingService;
	
	//connect ui to dispatcer
	connect imUI.rDispatcher to imDispatcher.pUI;
	
	//connect dispatcher to MessengerService
	connect imDispatcher.rService to imService.pDispatcher;
	
	instance audio : TelephoneConnection feature TelephoneIntegration;
	instance audio : VOIPConnection      feature VoiceOverIP;
	
	connect imService.audioCon to audio.audioCon;
}
```

The deliverable system "collabsys" is associated to the feature "SynchronousConferencing". This means that the above deliverable system and the contained elements are deleted, if the feature "SynchronousConferencing" doesn't occur in the current configuration. But a more interesting issue is variability in the wiring. This kind of variability is demonstrated in the listing by making a variable wire from the concrete "InstantMessagingService" to the possible audio connections (TelephoneConnection & VOIPConnection). You can see that there are two instances of audio connections with the same name "audio". Usually this is not allowed because it is ambiguous. However, because the two audio connection instances are dependent on features which are themselves mutually exclusive, the resulting system of the tailoring process is valid.