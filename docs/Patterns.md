# PATRONES DE DISEÑO  
	De acuerdo a la estructura planteada, utilizaremos los siguientes Patrones de Diseño:  

## Singleton  
- **Definición**: Patrón de Diseño Creacional que asegura que una clase tenga una única instancia, a la vez que proporciona un punto de acceso global para dicha instancia.  
- **Objetivo**: Con esto buscamos asegurar que las siguientes clases se instancien una sola vez:
	- Partida
	- Banco

## Strategy
- **Definición**: Patrón de Diseño de Comportamiento que permite definir una familia de algoritmos, colocar cada uno de ellos en una clase separada y hacer sus objetos intercambiables.
- **Objetivo**: Buscamos definir en un solo lugar que métodos habrá a través de una Interfaz (Interface) y delegar a cada clase como responder frente a cierta solicitud. Esto lo implementaremos en:
	- IPerfil, donde estarán definidos los métodos básicos y luego, las clases PerfilConservador, PerfilEquilibrado y PerfilAgresivo, definirán como funciona específicamente cada uno de ellos en su propia clase.

## Template Method
- **Definición**: Patrón de Diseño de Comportamiento que define el esqueleto de un algoritmo en una operación, permite que las subclases redefinan ciertos métodos del algoritmo pero, sin cambiar la estructura general de este.
- **Objetivo**: De esta forma definimos una estructura base en una clase Abstracta (Abstract) y dejamos que cada subclase defina como responder y, de paso, agregar ciertas funcionalidades adicionales:
	- Casillero, tendrá definidas ciertas cosas básicas, para que luego las clases Escritura, Servicio, Suerte y Destino hereden de ella y realicen los cambios y adiciones necesarias.