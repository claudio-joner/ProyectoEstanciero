# CLASES ABSTRACTAS E INTERFACES
	A continuación indicaremos clases abstractas e interfaces que  detectamos en nuestro proyecto:

# CLASES ABSTRACTAS


# INTERFACES

IPerfiles:
|    Method   |    Type    |          Description          |  
| --------------- | -------------- | ----------------------------------- |  
| `esPreferida(Provincia provincia)`| `bool`| Método que compara si la provincia que se pasa por argumento está dentro de sus preferencias. |  
| `iniciarProvPreferidas()` | `void`| Método que inicializa la lista de provincias preferidas. |
| `construirMejora(Casillero casillero, Jugador jugador);` | `void` | Método que le permite construir chacras o estancias al bot cuando este tiene una provincia completa. |
| `comprarPropiedad(T casillero,Jugador jugador)` | `bool` | Método que le setea un dueño a una escritura o servicio,modifica la cuenta del jugador y agregar el servicio o la escritura al listas del jugador. |
| `comprarPorPreferenciaPropiedadV2(T casillero,Jugador jugador)` | `bool` | Método que  verifica que una propiedad cumpla todos los requisitos de preferencia del perfil,sea un servicio o escritura. |
| `comprarFueraDePreferenciaPropiedad(T casillero,Jugador jugador)` | `bool` | Método que permite comprar propiedades fuera de preferencia segun los requisito para comprar fuera de las preferencias. |
| `contarEscriturasAdquiridasPorZona(Escritura escritura)` | `void` | Método que verifica si la provincia de una escritura esta completa. |

