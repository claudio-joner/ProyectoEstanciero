# EXPERIENCIA DE USUARIO
	Vamos a utilizar la interfaz de Consola.
	Antes del comienzo del turno del usuario, se mostrara en pantalla un resumen de los movimientos de los demas jugadores (bots).  
	Para acceder a los menus y submenus del juego, se listaran  de 1 a n opciones, donde el jugador debera elegir el número correspondiente para ingresar al menu.  
	Todos los menus tienen la opción de Volver al menu anterior.  
	Por cada turno que pase, el usuario tendra su información actualizada en pantalla.

A continuación detallaremos todos los menus del juego:

##### 1) Menu "Principal"
- 1. Iniciar Nuevo Juego
- 2. Cargar Juego Guardado
- 3. Ver Reglas del Juego
- 4. Salir

###### Eventos y acciones
**- Iniciar Nuevo Juego**: ingresando en esta opción se navega al siguiente menu, Dificultad.  
**- Cargar Juego Guardado**: permite a seleccionar que partida se puede cargar.
**- Ver Reglas**: permite ver las reglas del juego, Objetivos, Cantidad de Propiedades, Tablero, Modalidades del Juego, Perfiles de jugadores etc.  
**- Salir**: finaliza la ejecución del programa.  


##### 2) Menu "Dificultad"
- 	1)Fácil
- 	2)Medio
- 	3)Difícil

###### Eventos y acciones
**-  Dificultad**: indica las características de cada dificultad, el usuario debe seleccionar del 1 a 3, posteriormente avanza al menu Objetivo.


##### 3) Menu "Jugador":
###### Eventos y acciones
**-  Ingresar Nombre**: se pide al usuario que ingrese su nombre.
**-  Elegir Color**: se pide al usuario que seleccione el color de su peon, las opciones van de 1 a 7.
**-  Mostrar otros jugadores**: se muestra lo colores de todos los jugadores.

##### 4) Menu "Opciones de Victoria":
-	1)Objetivo

###### Eventos y acciones
**- Objetivo**: este menu pregunta cual es el modo de juego, si por monto maximo de victoria o banca rota, en caso de que eliga si por moton máximo debe ingresar un entero mayor a 35000.


##### 4)Menu "Iniciando juego":

###### Eventos y acciones
**- Imprime en pantanlla**: imprime en pantalla el orden de los turnos de los jugadores,con su colores y por ultimo mostrar el objetivo de la partida.Además muestra el turno del primer jugador


##### 5) Menu "Mi Turno"
- 1. Tirar Dados
- 2. Ver Propiedades
- 3. Vender Propiedad
- 4. Construir Mejoras
- 5. Ver Saldo

###### Eventos y acciones
**- Tirar Dados**: se tiran los dados y hace que el usuario avance,r si desea esperar o no, en caso de carcel se le pide tirar los dado ,a continuacion se detalla el menu de casillero especial.   
**- Ver Propiedades**: listado completo de las propiedades del usuario, ordenadas por provincia.   
**- Vender Propiedad**: lista de la propiedades del usuario para que este elija cual vender.
**- Construir Mejoras**: permite gestionar las propiedades a disposición, se listarán aquellas que si puedan ser realizadas.  
**- Ver Saldo**: permite ver la cuenta del usuario.


#####  6) Menu "Casillero":
- 	1)Acción
    * 1)Comprar/Pasar/Mejorar/AccionesEspeciales

###### Eventos y acciones
**- Casillero**: menu o interfaz que aparece luego de tirar los dados, posee submenus Acción (sucede un evento dependiendo el tipo de casillero).

- Si es una propiedad disponible(no tiene dueño):
    * Comprar: indica por si o no, se descuenta el monto de la cuenta del jugador

- Si es un evento:
    * Realiza la acción, se imprime un mensaje del evento.
    * Posibles eventos:
      * Comisaría: se muestra el siguiente submenu con dichas opciones:
            * 1 - pagar $1000
            * 2 - usar tarjeta para salir
            * 3 - sacar dobles con los dados
      * Marche comisaría: se desplaza el peón del jugador a la casilla de comisaria
      * Carta de suerte: realiza las acciones de las carta y la devuelve al mazo.
      * Carta de destino: realiza las acciones de las carta y la devuelve al mazo.
      * Descanso: el usuario puede elegir permanecer en ese casillero por dos turnos o seguir su camino
      * Salida: se suma a la cuenta del jugador x cantidad de dinero
      * Premio Ganadero: se suma a la cuenta del jugador x cantidad de dinero

  - Si el casillero es un servicio:
      * Idem a casillero
    
    Finalizado el turno muesta el tablero actualizado, con las posiciones de cada jugador y actualizaciones de compra,además brinca un resumen con los datos del jugador que jugó el turno


#####  7)Menú Continua partida:

###### Eventos y acciones
**- Continuar partida**: se pregunta al jugador si  desa continuar con la partida, en caso de continuar se juega hasta que alguien gana.Al ganar se muesta el nombre del jugador, el objetivo de la partida, fecha de creacion de la partida, tiempo de juego, entre otros.

#####  7)Menú Nueva partida:

###### Eventos y acciones
**- Nueva partida**: se pregunta al jugador si  desea crear una nueva partida.Al ser efectiva empieza una nueva partida.


