1) Actualizar el código del repositorio usando alguna herramienta como Rapidsvn tortoisesvn, svn etc
No hagan el primer checkout usando el plugin subclipse porque no va a funcionar. Cuando usás
el plugin svn para eclipse te trae el projecto desde el repositorio, pero como un proyecto común y no como un proyecto android.

2) Ahora si desde eclipse creen un proyecto andorid desde codigo existente. En esos pasos eligen los tres proyectos (appcompatV7, citybike y googleplay service).

3) Cuando termina de hacer build debería estar todo ok sin errores. A partir de este momento pueden usar el plugin svn de eclipse.
Es importante que ahora se paren a nivel de la carpeta trunk (donde está este readme) y usen el archivo oculto para filtrar los archivos que no queremos
que vayan al repositorio. Para eso hagan esto: $svn propset svn:ignore -R -F .svnignore .
OJO que el último punto no es el punto que indica el directorio actual. Tienen que ponerlo también.
Si abren el archivo .svnignore con un editor de texto van a ver la lista de cosas que no se suben.
Listo. En este punto ya etán en condiciones de usar el repo.

4) Para evitar el problema de la clave de google map, copien el archivo debug.keystore para que quede acá:
/home/SUUSUARIO/.android/debug.keystore

Ya deberían poder ejecutar el tp (teniendo en cuenta que instalaron el plugin de android en eclipse etc.).
