# Informe Taller Componentes: Component Quest

## 1. Captura de las librerías

Librerías .jar cargadas en juego-principal y salida final por consola.

<img width="300" height="195" alt="image" src="https://github.com/user-attachments/assets/1b70e095-7127-4e3c-bb2c-41bf2682bf77" />

<img width="715" height="264" alt="image" src="https://github.com/user-attachments/assets/7e505552-7e44-48bf-9696-9e7ff7b6ed5d" />


Salida obtenida:

```
PRUEBA FINAL COMPONENT QUEST

Ronda 1 Dragon Ignis
Ataque con espada: 50 pts

Ronda 2 Golem Sismico
Con botas, escudo activo: true
Con botas, proteccion: 90
Sin botas, escudo activo: true
Sin botas, proteccion: 60

Ronda 4 Espectro Cambiante
Casco de hierro ante fuego: El casco de hierro no soporta ataques de FUEGO
Casco runico ante fuego: Ataque de FUEGO absorbido por las runas
```

## 2. Parte 1: Diagnóstico inicial

### 2.1 Empaquetado de Peto y Botas

<img width="1259" height="516" alt="image" src="https://github.com/user-attachments/assets/2d43e439-2b51-4987-b596-07b056e322cf" />

<img width="1300" height="605" alt="image" src="https://github.com/user-attachments/assets/5bd4c0fb-9358-45a7-af2f-9c26adb809ac" />


**Por qué ocurre:** PetoEnlace importa BotasEnlace y BotasEnlace importa PetoEnlace. Forman un ciclo de dependencias. Para compilar modulo-peto se necesitan las clases de modulo-botas, y para compilar modulo-botas se necesitan las de modulo-peto. Ninguno de los dos puede compilarse primero, por eso el IDE marca que el paquete no existe y no se puede generar peto.jar ni botas.jar de forma independiente.

**Principio incumplido:** ADP (Acyclic Dependencies Principle), que exige que el grafo de dependencias entre componentes no tenga ciclos.

**Relación con el síndrome de la mañana siguiente:** al estar acoplados en ciclo, un cambio en uno de los componentes rompe al otro, y los dos tienen que integrarse y probarse juntos. Lo que funcionaba el día anterior deja de funcionar al día siguiente porque alguien modificó el otro componente. Es lo mismo que pasó en la actividad en clase: al retirar una pieza del ciclo, la otra dejó de funcionar.

### 2.2 Ejecución inicial de Espada y Casco
<img width="311" height="149" alt="image" src="https://github.com/user-attachments/assets/38813f9d-182f-463c-8296-9ac654628f30" />

<img width="638" height="129" alt="image" src="https://github.com/user-attachments/assets/3ce6f916-a079-48a3-bf8c-ad5795c1c3d9" />

Salida obtenida:

```
PRUEBA INICIAL: COMPONENT QUEST
Ataque con espada: -50 pts
Defensa del casco: CASCO DESTRUIDO: No soporta ataques de FUEGO
```

**Qué sucede:**
- La espada ataca con -50 puntos porque EspadaExplorador resta el peso de ArmaduraMarmol (50 - 100).
- El casco queda destruido ante el ataque de fuego porque solo sabe resistir ataques físicos.

**Fallas en modulo-espada:**
- **CRP (Common Reuse Principle):** quien reutiliza EspadaExplorador queda obligado a cargar ArmaduraMarmol, una clase que no necesita.
- **CCP (Common Closure Principle):** una espada y una armadura no cambian por las mismas razones, pero están en el mismo componente.
- **REP (Reuse/Release Equivalence Principle):** la espada no puede reutilizarse ni versionarse sola, porque va ligada a la armadura.

**Fallas en modulo-casco:**
- **SAP (Stable Abstractions Principle):** CascoHierroSellado es un componente estable, pues otros dependen de él, pero es una clase concreta sin abstracciones, así que es difícil de extender.
- **SDP (Stable Dependencies Principle):** los dependientes se apoyan en una clase estable y rígida. Para soportar nuevos elementos habría que modificarla, lo que afecta a todos los que dependen de ella.

**Ubicación en la gráfica Abstracción vs. Inestabilidad:**
- cascoHierroSellado: **Zona de Dolor** (muy estable y nada abstracta, A = 0, I = 0).
- GemaOlvidada: **Zona de Inutilidad** (muy abstracta pero nadie la usa, A = 1, I = 1).

## 3. Tabla de metricas

Fórmulas:

- Inestabilidad: I = Ce / (Ca + Ce)
- Abstracción: A = Na / Nc
- Distancia a la Secuencia Principal: D = |A + I − 1|

| Componente | Ca | Ce | I | A | D | Zona / Diagnóstico |
|---|---|---|---|---|---|---|
| core-v1.0.jar (Final) | 2 | 0 | 0 | 1 | 0 | Sobre la Secuencia Principal |
| peto-v1.0.jar (Final) | 1 | 1 | 0,5 | 0 | 0,5 | Cerca de la Secuencia Principal, algo hacia la Zona de Dolor |
| casco-v1.0.jar (Final) | 1 | 0 | 0 | 0,33 | 0,67 | Zona de Dolor |
| CascoHierroSellado (Inicial) | 3 | 0 | 0 | 0 | 1 | Zona de Dolor |
| GemaOlvidada (Inicial) | 0 | 0 | 1 | 1 | 1 | Zona de Inutilidad |

### Procedimiento

**core-v1.0.jar** (1 interfaz, 1 clase en total)
- I = 0 / (2 + 0) = 0
- A = 1 / 1 = 1
- D = |1 + 0 − 1| = 0

**peto-v1.0.jar** (0 abstractas, 1 clase en total)
- I = 1 / (1 + 1) = 0,5
- A = 0 / 1 = 0
- D = |0 + 0,5 − 1| = 0,5

**casco-v1.0.jar** (1 interfaz, 3 clases en total: ProteccionElemental, CascoHierroSellado, CascoRunico)
- I = 0 / (1 + 0) = 0
- A = 1 / 3 ≈ 0,33
- D = |0,33 + 0 − 1| ≈ 0,67

**CascoHierroSellado inicial** (Ca = 3, Ce = 0, clase concreta)
- I = 0 / (3 + 0) = 0
- A = 0 / 1 = 0
- D = |0 + 0 − 1| = 1

**GemaOlvidada inicial** (Ca = 0, Ce = 0, interfaz)
- I = 0 / (0 + 0) queda indeterminada. Como nadie depende de ella ni ella de nadie, se toma I = 1 por convención.
- A = 1 / 1 = 1
- D = |1 + 1 − 1| = 1
