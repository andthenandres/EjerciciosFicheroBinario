import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;


public class FicheroBinario {
    static Scanner scanner = new Scanner(System.in);
    static String ruta = "src/txt.dat";
    public static void main(String[] args) throws IOException {

    int eleccion = 0;

        do {

            try {
                System.out.println("\n" +
                        "--- GESTIÓN DE INVENTARIO --- \n" +
                        "1. Alta de producto \n" +
                        "2. Ver todos los productos \n" +
                        "3. Buscar producto por nombre \n" +
                        "4. Listar productos por categoría \n" +
                        "5. Mostrar productos económicos (precio < 30€) \n" +
                        "6. Mostrar productos premium (precio >= 50€) \n" +
                        "7. Salir \n" +
                        "Elige una opción:");
                eleccion = scanner.nextInt();
            }catch (InputMismatchException e){
                System.out.println("Introduce un numero por favor");
            }
            if(eleccion < 1 || eleccion > 7){
                System.out.println("Introduce un numero entre las opciones indicadas");
            }


        }while(eleccion < 1 || eleccion > 7);

        switch (eleccion){

            case 1:
                altaProducto();
                break;
            case 2:
                mostrarTodosProductos();
                break;
            case 3:
                buscarPorNombre();
                break;
            case 4:
                listarPorCategoria();
                break;
            case 5:
                mostrarProductosEconomicos();
                break;
            case 6:
                mostrarProductosPremium();
                break;
            case 7:
                break;
        }

    }

//funcion auxiliares
    public static double comprobarNumero(String mensaje){

        double num = 0;

        do{
            try {
                System.out.println(mensaje);
                num = scanner.nextDouble();

                if(num <= 0){
                    System.out.println("Introduce un numero mayor o igual que cero");
                }

            } catch (InputMismatchException e) {
                System.out.println("Introduce un numero");
                scanner.nextLine();
            }
        }while (num <= 0);

        return num;
    }







//funciones GESTION INVENTARIO

//1 OPCION
    public static void altaProducto() throws IOException {
        /*
1. Alta de producto
Solicita al usuario los siguientes datos por teclado:
Nombre (cadena de texto)
Categoría: El programa mostrará un submenú para seleccionar obligatoriamente
una de las siguientes 4 categorías:
Alimentación
Bebidas
Droguería
Electrónica
Stock/Cantidad (número entero)
Precio (número decimal)
         */
        String nombre = "";
        int eleccion = 0;
        String categoriaProducto = "";
        int stock = 0;
        double precio = 0.0;

        //Creo el fichero y creo la variable para escribir en el fichero
        FileOutputStream fos = new FileOutputStream(ruta, true);
        DataOutputStream dos = new DataOutputStream(fos);

        //Pido los valores
        System.out.println("Introduce un nombre");
        nombre = scanner.nextLine();ç
                
        do{
            try {
                System.out.println("Elige una de las 4 siguientes categorias \n" +
                        "-1 Alimentación\n" +
                        "-2 Bebidas\n" +
                        "-3 Droguería\n" +
                        "-4 Electrónica");
                eleccion = scanner.nextInt();
            }catch (InputMismatchException e){
                System.out.println("Introduce un numero");
            }
        }while((eleccion > 4 || eleccion < 1 ));
        switch(eleccion){
            case 1:
                categoriaProducto = "Alimentacion";
                break;
            case 2:
                categoriaProducto = "Bebidas";
                break;
            case 3:
                categoriaProducto = "Drogueria";
                break;
            case 4:
                categoriaProducto = "Electronica";
                break;
        }
        stock = (int) comprobarNumero("Introduce un stock");
        precio = comprobarNumero("Introduce un precio");

        //Escribo en el archivo
        dos.writeUTF(nombre);
        dos.writeUTF(categoriaProducto);
        dos.writeInt(stock);
        dos.writeDouble(precio);

        //Cierro el archivo
        fos.close();
        dos.close();
    }
//2 OPCION
    public static void mostrarTodosProductos() throws IOException {
/*
Lee el archivo binario desde el principio y muestra en consola la lista completa
con todos los productos registrados (nombre, categoría, stock y precio)
 */
        //Creo el FInputStream para indicar el archivo a leer y el DInputStream para leerlo
        FileInputStream fis = new FileInputStream(ruta);
        DataInputStream dis = new DataInputStream(fis);

        while(true){
            try {
                System.out.println(dis.readUTF());
                System.out.println(dis.readUTF());
                System.out.println(dis.readInt());
                System.out.println(dis.readDouble());
            }catch (IOException e){
                System.out.println("Fin del archivo");
                break;
            }
        }
        fis.close();
        dis.close();


    }
//3 OPCION
    public static void buscarPorNombre() throws IOException {
        String nombre = "";
        FileInputStream fis = new FileInputStream(ruta);
        DataInputStream dis = new DataInputStream(fis);
        String nombreP = dis.readUTF();

        System.out.println("Introduce un nombre de producto");
        nombre = scanner.nextLine();
        if(nombre.equalsIgnoreCase(nombreP)){
            System.out.println("El producto existe");
            System.out.println("Nombre: " + dis.readUTF() + "\n" +
                    "Categoria: " +dis.readUTF() + "\n" +
            "Stock: " + dis.readInt() + "\n" +
            "Precio: " + dis.readDouble()+"\n");
        }
        else{
            System.out.println("El producto no existe");
        }
    }
//4 OPCION
    public static void listarPorCategoria() throws IOException {
/*
Muestra al usuario las 4 categorías disponibles para que elija cuál quiere consultar
(1. Alimentación, 2. Bebidas, 3. Droguería, 4. Electrónica).
Recorre el archivo binario y muestra únicamente los productos que pertenezcan a
la categoría seleccionada. Si no hay productos guardados en ella,
 muestra un mensaje de aviso.
 */
        int categoria = 0;
        String categoriaProducto = "";
        FileInputStream fis = new FileInputStream(ruta);
        DataInputStream dis = new DataInputStream(fis);
        String nombreP = dis.readUTF();
        String categoriaP = dis.readUTF();
        int stockP = dis.readInt();
        int precioP = dis.readInt();
        do {
            System.out.println("Que categoria quieres mostrar");
            categoria = scanner.nextInt();
            if(categoria < 1 || categoria > 4){
                System.out.println("Introduce una categoria");
            }
        }while(categoria < 1 || categoria > 4);
        categoriaProducto = switch (categoria) {
            case 1 -> "Alimentacion";
            case 2 -> "Bebidas";
            case 3 -> "Drogueria";
            case 4 -> "Electronica";
            default -> categoriaProducto;
        };
        if(categoriaProducto.equalsIgnoreCase(categoriaP)){
            System.out.println("La categoria del producto existe");
            System.out.println("Nombre: " + nombreP + "\n" +
                    "Categoria: " + categoriaP + "\n" +
                    "Stock: " + stockP + "\n" +
                    "Precio: " + precioP +"\n");
        }
        else{
            System.out.println("La categoria del producto no existe");
        }
    }
//5 OPCION
    public static void mostrarProductosEconomicos() throws IOException {
/*
Recorre el fichero binario e imprime en pantalla únicamente
aquellos productos cuyo precio sea estrictamente menor que 30,00 €
 */

        FileInputStream fis = new FileInputStream(ruta);
        DataInputStream dis = new DataInputStream(fis);
        String nombreP = dis.readUTF();
        String categoriaP = dis.readUTF();
        int stockP = dis.readInt();
        int precioP = dis.readInt();

        System.out.println("Productos menores de 30 euros: \n");

        if(precioP < 30){
            System.out.println("Nombre: " + nombreP + "\n" +
                    "Categoria: " + categoriaP + "\n" +
                    "Stock: " + stockP + "\n" +
                    "Precio: " + precioP +"\n");
        }
    }
//6 OPCION
    public static void mostrarProductosPremium() throws IOException {
        FileInputStream fis = new FileInputStream(ruta);
        DataInputStream dis = new DataInputStream(fis);
        String nombreP = dis.readUTF();
        String categoriaP = dis.readUTF();
        int stockP = dis.readInt();
        int precioP = dis.readInt();

        System.out.println("Productos premium de 50 euros o mas : \n");

        if(precioP >= 50){
            System.out.println("Nombre: " + nombreP + "\n" +
                    "Categoria: " + categoriaP + "\n" +
                    "Stock: " + stockP + "\n" +
                    "Precio: " + precioP +"\n");
        }
    }
}


