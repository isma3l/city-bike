
INSTALACIÓN
------------
*) Para un ide eclipse existente:
	1) Descargar andorid sdk de este lugar: 
		https://developer.android.com/sdk/index.html#download
	2) Una vez descargado, descompriman en algún lugar de su máquina.
	3) Instalen el plugin de android y configuren como dice acá:
		https://developer.android.com/sdk/installing/installing-adt.html
	4) Agreguen plataformas y paquetes lanzando SDK manager.
		Vayan en eclipse a window/Android SDK manager
		Ya les va a salir tildado la mayoría de las cosas a instalar. Es algo
		similar al screenshot de esta página:
			https://developer.android.com/sdk/installing/adding-packages.html
		Además de los que ya les tilda automáticamente, vayan a Extras y tilden:
			*) Android support repository
			*) Android support library
			*) Google play services
		Instalen los paquetes seleccionados tocando el botón de abajo a la derecha.
		(Va a llevar un poco de tiempo esto)
	
OBTENER CÓDIGO DEL TP	
---------------------

*) Actualizar el código del repositorio usando alguna herramienta como Rapidsvn,
	tortoisesvn, svn etc (Usen lo que más les guste)
	No hagan el primer checkout usando el plugin subclipse porque no va a 
	funcionar. Cuando usás el plugin svn para eclipse te trae el projecto desde 
	el repositorio, pero como un proyecto común y no como un proyecto android.

*) Ahora si desde eclipse creen un proyecto andorid desde codigo existente. 
	Busquen a CityBike donde lo tengan.
*) Una vez creado el proyecto, les va a aparecer CityBike con un signo de admira-
	-ción rojo, con problemas en la carpeta res, en todas las carpetas values,
	en los archivos styles.xml
	Para solucionar esto hay que traer a appcompat v7 y google play services como
	librerías.
	Traigo appcompatV7:
		*) File->import
		*) Android - existing android code into workspace (y next)
		*) En root directory tocan el botón Browse y se van a la carpeta donde
			descomprimieron android SDK (en el paso 2 de instalación (arriba)).
			Van a la carpeta Extras/Android/Support/v7/appcompat y tocan aceptar.
			Cuando vuelvan, tocan Finish y esperan unos segundos a que eclipse
			cargue y buildee el wrokspace. Cuando termine les va a aparecer el
			proyecto que importaron.
		*) click con el derecho sobre el proyecto CityBike, properties.
		*) En android, controlen que en el sector de Library aparesca con un 
			tilde verde android-support-v7-appcompat. Si es así van a ver que
			ya no hay más problemas con los style.xml (Desapareció el error de
			la carpeta res y todas las carpetas values..)
	Sin embargo sigue el signo de admiración rojo en CityBike. Ahora
	tienen que tener un problema con AndroidManifiest.xml.
	Hagamos lo mismo otra vez:
	Traigo google play services:
		*) File->import
		*) En root directory tocan el botón Browse y se van a la carpeta donde
			descomprimieron android SDK (en el paso 2 de instalación (arriba)).
			Van a la carpeta extras/google/google_play_services/librpoject y tocan 
			aceptar. Cuando vuelvan, tocan Finish y esperan unos segundos a que 
			eclipse cargue y buildee el wrokspace. Cuando termine les va a aparecer 
			el proyecto que importaron.
		*) click con el derecho sobre el proyecto CityBike, properties.
		*) En android, controlen que en el sector de Library aparesca con un 
			tilde verde google_play_services. Si no está, tocan el botón add y lo
			eligen. Le dan ok y van a ver en este punto que el proyecto no tiene
			ningún error.
REPOSITORIO
-----------		
A partir de este momento pueden  usar el plugin svn de eclipse si 
quieren.
Es importante que ahora (antes de comitear algo) se paren a nivel de la carpeta 
trunk (donde está este readme) y usen el archivo oculto para filtrar los archivos 
que no queremos que vayan al repositorio. Para eso hagan esto desde la terminal: 
				$svn propset svn:ignore -R -F .svnignore .
OJO que el último punto no es el punto que indica el directorio actual. Tienen 
que ponerlo también. Si abren el archivo .svnignore con un editor de texto van a 
ver la lista de cosas que no se suben.
Listo. En este punto ya etán en condiciones de usar el repo tranquilamente.

IMPORTANTE
----------
4) Para evitar el problema de la clave de google map, copien y reemplacen el 
archivo debug.keystore acá: /home/SUUSUARIO/.android/debug.keystore
Hagan project->clean..->clean all project y esperen al build automático
Ya deberían poder ejecutar el tp. Click con el derecho sobre CityBike- run as->
android application. conecten un celular con android a la máquina mediante un cable
usb o sino creen un emulador. Si le preguntan si quiere mostrar mensajes logcat
diagnle que si proque es útil.
