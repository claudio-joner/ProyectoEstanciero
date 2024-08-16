


#####  6) Menu "Casillero":
- 	1)Accion
	* 	1)Comprar/Pasar/Mejorar/AccionesEspeciales
- 	2)(SM)Informacion
	* 	1)Volver

###### Eventos y acciones  
**- Casillero**:Menu o pantalla luego de tirar los dados,posee 2 menus Accion(sucede un evento dependiendo el tipo de casillero) e Informacion.
**- Informacion**: muestra la informacion del casillero segun el tipo de casillero:
- Si es una propiedad disponible:
	* Nombre:nombre de la propiedad
	* Precio:precio de comprar de la propiedad
	* Alquiler:precio de pago de alquiler, cambia si tiene chacras, estancias o no tiene nada.
	* Zona:color de la zona a la que pertenece una propiedad.
- Si tiene dueño:
	* VerPropietario
	* PrecioAlquiler             
- Si es un evento:
	Mostrar la descripcion del evento.
**- Acciones**: acciones disponibles segun el tipo de casillero:
- Si el casillero es una propiedad:
	* Comprar: comprar propiedad y actualiza el monto de la cuenta,se vuelve al menu lanzarDados.
    * Pasar:en este caso sigue el proximo jugado
- Si tiene dueño:
	* Mejorar:se analizar si el usuario tiene propiedades de la misma zona.Si tiene todas las proppiedades de una misma zona, el usuario puede comprar chacra/estancia al banco, caso contrario se muestra el siguiente mensaje "Le faltan x propiedades para completar la zona"
	* Pagar:se paga el alquiler al dueno de la propiedad, actualiza la cuenta del jugador.Si el jugador no tiene fondos suficientes debara  Vender sus propiedades al banco o jugadores o Hipotecarlas, si es el jugador es un bot solo puede Vender sus propiedades al banco o Hipotecarlas         
- Si es un evento:se muestra la descripcion del evento y acciones
    * Comisaría: se esta hasta que no se saquen dobles.
    * Marche comisaría: se desplaza el peon a la casilla de comisaria.
    * Carta de suerte: se puede avanzar x cantidad de casilleros o se suma a la cuenta del jugador x cantidad de dinero.
    * Carta de destino se puede retrocedes x cantidad de casilleros o se  resta a la cuenta del jugador x cantidad de dinero.
    * Descanso:el usuario puede permanecer en ese casillero 2 turnos .
    * Salida: se suma a la cuenta del jugador x cantidad de dinero.
    * Premio Ganadero: se suma a la cuenta del jugador x cantidad de dinero.  