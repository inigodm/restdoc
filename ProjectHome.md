Project to document java REST services.

Tiny project to autogenerate documentation for existing REST services in java.

It's a REST service that describes REST services.

With restdoc you can document your services widely. It's an alpha, but the main goals are to document:

  * Describe REST services. What do they do, with verbs and noums ;)
  * Service paths and accepted mimetypes.
  * Produced and consumed objects (recursivelly!).

Also you can manage ALL the documentation that its generated or rely in custom annotations to document only that you want to document and showing what you want to show.

It have custom annotations to mark files which are REST services with the info that you want to share and annotations to mark the models that you want to be documented.

It generates a JSON document with that info and serves it in the sme server.

It's functional using RESTDOC's annotations over servlets-based application.

In future releases it will let autogenerate documentation from JAX-RS (reading info from its annotations and reflections) and will work with play framework-