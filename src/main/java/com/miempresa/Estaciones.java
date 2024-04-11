package com.miempresa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Estaciones {
    private String nombre;
    private int ID;
    private int linea;
    private boolean isNode;

    public Estaciones(String nombre, int ID, int linea, boolean node) {
        this.nombre = nombre;
        this.ID = ID;
        this.linea = linea;
        this.isNode = node;
    }

    public Estaciones(Estaciones estacion) {
        this.nombre = estacion.getNombre();
        this.ID = estacion.getID();
        this.linea = estacion.getLinea();
        this.isNode = estacion.isNode();
    }


    public boolean isNode() {
        return isNode;
    }

    public void setNode(boolean node) {
        isNode = node;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public static ArrayList<Estaciones> quickSort(ArrayList<Estaciones> array, int lowIndex, int highIndex) {
        if (lowIndex < highIndex) {
            int partitionIndex = partition(array, lowIndex, highIndex);

            quickSort(array, lowIndex, partitionIndex - 1);
            quickSort(array, partitionIndex + 1, highIndex);
        } else {
            return array;
        }
        return array;
    }

    static int partition(ArrayList<Estaciones> array, int lowIndex, int highIndex) {
        Estaciones pivot = array.get(highIndex);
        int i = (lowIndex - 1);

        for (int j = lowIndex; j < highIndex; j++) {
            if (array.get(j).getNombre().compareToIgnoreCase(pivot.getNombre()) < 0) {
                i++;
                swap(array, i, j);
            }
        }
        swap(array, i + 1, highIndex);
        return (i + 1);
    }

    static void swap(ArrayList<Estaciones> arr, int i, int j) {
        Collections.swap(arr, i, j);
    }

    public static int binarySearch(Estaciones[] arr, String nombre) {
        int l = 0, r = arr.length - 1;
        while (l <= r) {
            int m = l + (r - l) / 2;
            if (arr[m].getNombre().equals(nombre))
                return arr[m].getID();

            if (arr[m].getNombre().compareToIgnoreCase(nombre) < 0)
                l = m + 1;

            else
                r = m - 1;
        }

        return -1;
    }

    public static int linearSearch(ArrayList<Estaciones> arr, String nombre) {
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).getNombre().equals(nombre)) {
                return i;
            }
        }
        return -1;
    }

    private static int linearSearchLinea(ArrayList<Estaciones> metro, int estacionUltimoNodo, int estacionDestino)  {
        for (int i = 0; i < metro.size(); i++) {
            if (metro.get(i).getNombre().equals(metro.get(estacionUltimoNodo).getNombre()) &&
                metro.get(i).getLinea() == metro.get(estacionDestino).getLinea()) {
                return i;
            }
        }
        return -1;
    }


    public static int[] binarySearchRepeating(ArrayList<Estaciones> arr, int minimo, int maximo, String nombre, int total) {
        int[] respuesta = new int[2];
        respuesta[0] = primero(arr, minimo, maximo, nombre, total);
        respuesta[1] = ultimo(arr, minimo, maximo, nombre, total);
        return respuesta;
    }

    private static int primero(ArrayList<Estaciones> arr, int minimo, int maximo, String nombre, int total) {
        if (maximo >= minimo) {
            int mid = minimo + (maximo - minimo) / 2;
            if ((mid == 0 || nombre.compareToIgnoreCase(arr.get(mid - 1).getNombre()) > 0)
                    && nombre.compareToIgnoreCase(arr.get(mid).getNombre()) == 0)
                return mid;
            else if (nombre.compareToIgnoreCase(arr.get(mid).getNombre()) > 0)
                return primero(arr, (mid + 1), maximo, nombre, total);
            else
                return primero(arr, minimo, (mid - 1), nombre, total);
        }
        return -1;
    }

    private static int ultimo(ArrayList<Estaciones> arr, int minimo, int maximo, String nombre, int total) {
        if (maximo >= minimo) {
            int mid = minimo + (maximo - minimo) / 2;
            if ((mid == total - 1 || nombre.compareToIgnoreCase(arr.get(mid + 1).getNombre()) < 0)
                    && nombre.compareToIgnoreCase(arr.get(mid).getNombre()) == 0)
                return mid;
            else if (nombre.compareToIgnoreCase(arr.get(mid).getNombre()) < 0)
                return ultimo(arr, minimo, (mid - 1), nombre, total);
            else
                return ultimo(arr, (mid + 1), maximo, nombre, total);
        }
        return -1;
    }

    private static int buscarRuta(int[][] matT, int estacionInicial, int estacionFinal, ArrayList<Estaciones> metro) {
        //base case
        if (matT[estacionInicial][estacionFinal] == 99) {
            System.out.println("De " + metro.get(estacionInicial).getNombre() + " a " + metro.get(estacionFinal).getNombre() +
                    " hay linea directa");
            return matT[estacionInicial][estacionFinal];
        } else {
            System.out.println("De " + metro.get(estacionInicial).getNombre() + " a " + metro.get(matT[estacionInicial][estacionFinal]).getNombre());
            estacionInicial = matT[estacionInicial][estacionFinal];
            return buscarRuta(matT, estacionInicial, estacionFinal, metro);
        }
    }

    private static int buscarRutaNodoInicial(Estaciones destino, ArrayList<Estaciones> metro, int estacionInicial, int direccion) {
        //base case
        if (metro.get(estacionInicial).getNombre().equals(destino.getNombre())) {
            return estacionInicial;
        } else {
            if (direccion == 1) {
                System.out.println("De " + metro.get(estacionInicial).getNombre() + " pasando " + metro.get(estacionInicial + 1).getNombre());
                estacionInicial = ++estacionInicial;
                return buscarRutaNodoInicial(destino, metro, estacionInicial, direccion);
            } else {
                System.out.println("De " + metro.get(estacionInicial).getNombre() + " pasando " + metro.get(estacionInicial - 1).getNombre());
                estacionInicial = --estacionInicial;
                return buscarRutaNodoInicial(destino, metro, estacionInicial, direccion);
            }
        }
    }

    private static int buscarRutaNodo(Estaciones destino, ArrayList<Estaciones> metro, int estacionInicial, int direccion) {
        //base case
        if (metro.get(estacionInicial).equals(destino)) {
            return estacionInicial;
        } else {
            if (direccion == 1) {
                System.out.println("De " + metro.get(estacionInicial).getNombre() + " pasando " + metro.get(estacionInicial + 1).getNombre());
                estacionInicial = ++estacionInicial;
                return buscarRutaNodo(destino, metro, estacionInicial, direccion);
            } else {
                System.out.println("De " + metro.get(estacionInicial).getNombre() + " pasando " + metro.get(estacionInicial - 1).getNombre());
                estacionInicial = --estacionInicial;
                return buscarRutaNodo(destino, metro, estacionInicial, direccion);
            }
        }
    }

    private static ArrayList<Estaciones> buscarRutaFinal(int[][] matT, int estacionInicial, int estacionFinal, ArrayList<Estaciones> nodos,
                                                         ArrayList<Estaciones> nodosFloydRuta) {
        //base casemetro, estacionInicial, direccionInical);
        if (matT[estacionInicial][estacionFinal] == 99) {
            System.out.println("De " + nodos.get(estacionInicial).getNombre() + " a " + nodos.get(estacionFinal).getNombre() +
                    " hay linea directa");
            nodosFloydRuta.add(nodos.get(estacionInicial));
            nodosFloydRuta.add(nodos.get(estacionFinal));
            return nodosFloydRuta;
        } else {
            System.out.println("De " + nodos.get(estacionInicial).getNombre() + " a " + nodos.get(matT[estacionInicial][estacionFinal]).getNombre());
            nodosFloydRuta.add(nodos.get(estacionInicial));
            estacionInicial = matT[estacionInicial][estacionFinal];
            return buscarRutaFinal(matT, estacionInicial, estacionFinal, nodos, nodosFloydRuta);
        }
    }

    public static void buscarRutaLogica(int[][] matT, int estacionInicial, int estacionFinal, ArrayList<Estaciones> metro,
                                        ArrayList<Estaciones> nodos, ArrayList<Estaciones> nodosParaMatriz) {
        ArrayList<ArrayList<Estaciones>> resultadoNodo = new ArrayList<ArrayList<Estaciones>>();
        ArrayList<Estaciones> finalNodo = new ArrayList<>();
        boolean exito = false;
        int direccionInical;
        int transbordo;
        int direccionFinal;
        int destino;
        int primerNodo;
        int segundoNodo;
        ArrayList<Estaciones> nodosFloydRuta = new ArrayList<>();
        ArrayList<Estaciones> convertidas = new ArrayList<>();
        ArrayList<Estaciones> nodosMasCortos = new ArrayList<>();
        int contadorNodosCortos = 0;
        resultadoNodo = compartenNodo(metro, estacionInicial, estacionFinal);
        boolean primerapasado = true;
        for (int i = 0; i < resultadoNodo.getFirst().size(); i++) {
            for (int j = 0; j < resultadoNodo.getLast().size(); j++) {
                if (resultadoNodo.getFirst().get(i).getNombre().equals(resultadoNodo.getLast().get(j).getNombre())) {
                    finalNodo.add(resultadoNodo.getFirst().get(i));
                    finalNodo.add(resultadoNodo.getLast().get(j));
                    direccionInical = buscarDireccion(metro, metro.indexOf(finalNodo.getFirst()), estacionInicial);
                    Estaciones direcionNombreInicial = direcionNombre(metro, estacionInicial, direccionInical);
                    System.out.println("Partiendo de " + metro.get(estacionInicial).getNombre() + " direccion " + direcionNombreInicial.getNombre());
                    transbordo = buscarRutaNodo(finalNodo.getFirst(), metro, estacionInicial, direccionInical);
                    direccionFinal = buscarDireccion(metro, estacionFinal, metro.indexOf(finalNodo.getLast()));
                    Estaciones direccionNombreFinal = direcionNombre(metro, estacionFinal, direccionFinal);
                    System.out.println("Transbordo de " + metro.get(transbordo).getNombre() + "\nLinea " + metro.get(transbordo).getLinea() +
                            " a linea " + metro.get(estacionFinal).getLinea() + " direccion " + direccionNombreFinal.getNombre());
                    destino = buscarRutaNodo(metro.get(estacionFinal), metro, metro.indexOf(finalNodo.getLast()), direccionFinal);
                    System.out.println("A llegado a su destino estacion " + metro.get(destino).getNombre() + "!");
                    return;
                } else {
                    primerNodo = metroaMatriz(nodosParaMatriz, resultadoNodo.getFirst().get(i));
                    segundoNodo = metroaMatriz(nodosParaMatriz, resultadoNodo.getLast().get(j));
                    nodosFloydRuta = buscarRutaFinal(matT, primerNodo, segundoNodo, nodosParaMatriz, nodosFloydRuta);
                    if (primerapasado) {
                        primerapasado = false;
                        nodosMasCortos = cloneList(nodosFloydRuta);
                        contadorNodosCortos = nodosFloydRuta.size();
                    } else   if (nodosFloydRuta.size() < contadorNodosCortos && !primerapasado) {
                        nodosMasCortos = cloneList(nodosFloydRuta);
                        contadorNodosCortos = nodosFloydRuta.size();
                    }
                    nodosFloydRuta.clear();
                }
            }
        }
        System.out.println("\n\nRuta Final: ");
        convertidas = matrizaMetro(metro, nodosMasCortos);
        trasnbordosyRuta(metro, convertidas, estacionFinal, estacionInicial);
    }








    private static int metroaMatriz(ArrayList<Estaciones> nodosParaMatriz, Estaciones estacionBuscar) {
        int estacionConvertida = -1;
        for (int i = 0; i < nodosParaMatriz.size(); i++) {
            if (nodosParaMatriz.get(i).getNombre().equals(estacionBuscar.getNombre())) {
                estacionConvertida = i;
                return estacionConvertida;
            }
        }
        return estacionConvertida;
    }

    private static ArrayList<Estaciones> matrizaMetro(ArrayList<Estaciones> metro, ArrayList<Estaciones> nodosFloydRuta) {
        ArrayList<Estaciones> convertidas = new ArrayList<>();
        ArrayList<Estaciones> ahoraSiConvertidas = new ArrayList<>();
        ArrayList<Estaciones> primerArreglo = new ArrayList<>();
        ArrayList<Estaciones> segundArreglo = new ArrayList<>();
        boolean hayEstaciones = false;
        boolean primerArregloLleno = false;
        boolean segundoArregoLLeno = false;
        int contadorAparte = 1;
        int contadorAparteDos = 0;
        for (int i = 0; i < nodosFloydRuta.size(); i++) {
            for (int j = 0; j < metro.size() ; j++) {
                if (nodosFloydRuta.get(i).getNombre().equals(metro.get(j).getNombre())) {
                    convertidas.add(metro.get(j));
                    //estacionInicial = nodosFloydRuta.get(i +  1);
                }
            }
        }
//        medioConvertidas = convertidas.size();
//        hayEstaciones = true;
//        for (int i = 0; i < medioConvertidas; i++) {
//            for (int j = 0; j < metro.size(); j++) {
//                if (convertidas.get(i).getNombre().equals(metro.get(j).getNombre()) && convertidas.get(i).getLinea() != metro.get(j).getLinea() &&
//                !convertidas.contains(metro.get(j))) {
//                    convertidas.add(i, metro.get(j));
//                }
//            }
//        }
        boolean llegoalFinal = true;
        primerArreglo.add(convertidas.getFirst());
        while (llegoalFinal) {
            for (int i = contadorAparte; i < convertidas.size(); i++) {
                if (primerArreglo.isEmpty()) {
                    primerArreglo.add(convertidas.get(i));
                    contadorAparte++;
                    continue;
                }
                if (primerArreglo.getLast().getNombre().equals(convertidas.get(i).getNombre())) {
                    primerArreglo.add(convertidas.get(i));
                } else if (!primerArreglo.getLast().getNombre().equals(convertidas.get(i).getNombre()) && segundArreglo.isEmpty()) {
                    primerArregloLleno = true;
                    segundArreglo.add(convertidas.get(i));
                } else if (segundArreglo.getLast().getNombre().equals(convertidas.get(i).getNombre())) {
                    segundArreglo.add(convertidas.get(i));
                } else if (!segundArreglo.getLast().getNombre().equals(convertidas.get(i).getNombre())) {
                    segundoArregoLLeno = true;
                    break;
                }
                contadorAparte++;
            }
            if (contadorAparte == convertidas.size()) {
                segundoArregoLLeno = true;
                llegoalFinal = false;
            }
            if (primerArregloLleno && segundoArregoLLeno) {
                for (int i = 0; i < primerArreglo.size(); i++) {
                    contadorAparteDos++;
                    for (int j = 0; j < segundArreglo.size(); j++) {
                        if (primerArreglo.get(i).getLinea() == segundArreglo.get(j).getLinea()) {
                            ahoraSiConvertidas.add(primerArreglo.get(i));
                            ahoraSiConvertidas.add(segundArreglo.get(j));
                        }
                    }
                }
                contadorAparte = contadorAparteDos;
                primerArreglo.clear();
                primerArregloLleno = false;
                //primerArreglo.add(ahoraSiConvertidas.getLast());
                //primerArreglo = cloneList(segundArreglo);
                segundArreglo.clear();
                segundoArregoLLeno = false;
            }
        }

//        convertidas.clear();
//        convertidas.add(ahoraSiConvertidas.getFirst());
//        for (int i = 0; i < ahoraSiConvertidas.size(); i++) {
//            for (int j = 0; j < ahoraSiConvertidas.size(); j++) {
//                if (!ahoraSiConvertidas.get(i).equals(ahoraSiConvertidas.get(j)) && i != j) {
//                    convertidas.add(ahoraSiConvertidas.get(j));
//                }
//            }
//        }

        return ahoraSiConvertidas;
    }

    private static ArrayList<Estaciones> cloneList (ArrayList<Estaciones> estaciones) {
        ArrayList<Estaciones> clon = new ArrayList<>();
        for (Estaciones estaciones1:
            estaciones) {
            clon.add(new Estaciones(estaciones1));
        }
        return clon;
    }
    private static void trasnbordosyRuta(ArrayList<Estaciones> metro, ArrayList<Estaciones> nodosFloydRuta, int estacionDestino,
                                         int estacionInicial) {
        int direccionInical;
        int transbordo;
        int direccionFinal;
        int destino;
        int conversionUltNodo;
        Estaciones direcionNombreInicial;
        Estaciones direccionNombreFinal;
        direccionInical = buscarDireccion(metro, metro.indexOf(nodosFloydRuta.getFirst()), estacionInicial);
        direcionNombreInicial = direcionNombre(metro, estacionInicial, direccionInical);
        System.out.println("Partiendo de " + metro.get(estacionInicial).getNombre() + " direccion " +
                direcionNombreInicial.getNombre());
        int nodoInicial = buscarRutaNodoInicial(nodosFloydRuta.getFirst(), metro,estacionInicial, direccionInical);
        direccionFinal = buscarDirecciondeNodoaNodo(metro, metro.indexOf(nodosFloydRuta.getFirst()), metro.indexOf(nodosFloydRuta.get(1)));
        direcionNombreInicial = direcionNombre(metro, metro.indexOf(nodosFloydRuta.getFirst()), direccionFinal);
        System.out.println("Transbordo de " + metro.get(nodoInicial).getNombre() + "\nLinea " +  metro.get(nodoInicial).getLinea() +
                " a linea " + nodosFloydRuta.getFirst().getLinea() + " direccion " + direcionNombreInicial.getNombre());
        if (nodosFloydRuta.size() > 4) {
            for (int i = 0; i < nodosFloydRuta.size() - 2; i++) {
                direccionInical = buscarDirecciondeNodoaNodo(metro, metro.indexOf(nodosFloydRuta.get(i)), metro.indexOf(nodosFloydRuta.get(i + 1)));
                transbordo = buscarRutaNodo(metro.get(metro.indexOf(nodosFloydRuta.get(i + 1))), metro, metro.indexOf(nodosFloydRuta.get(i)), direccionInical);
                direccionFinal = buscarDirecciondeNodoaNodo(metro, metro.indexOf(nodosFloydRuta.get(i + 2)), metro.indexOf(nodosFloydRuta.get(i + 3)));
                direccionNombreFinal = direcionNombre(metro, metro.indexOf(nodosFloydRuta.get(i + 3)), direccionFinal);
                System.out.println("Transbordo de " + metro.get(transbordo).getNombre() + "\nLinea " + metro.get(transbordo).getLinea() +
                        " a linea " + nodosFloydRuta.get(i + 3).getLinea() + " direccion " + direccionNombreFinal.getNombre());
                i++;
            }
        }else if (nodosFloydRuta.size() == 2){
            direccionInical = buscarDirecciondeNodoaNodo(metro, metro.indexOf(nodosFloydRuta.get(0)), metro.indexOf(nodosFloydRuta.get(0 + 1)));
            transbordo = buscarRutaNodo(metro.get(metro.indexOf(nodosFloydRuta.get(1))), metro, metro.indexOf(nodosFloydRuta.get(0)), direccionInical);
            //direccionFinal = buscarDirecciondeNodoaNodo(metro, metro.indexOf(nodosFloydRuta.get(1)), estacionDestino);
            conversionUltNodo = linearSearchLinea(metro, metro.indexOf(nodosFloydRuta.get(1)),estacionDestino);
            direccionFinal = buscarDireccion(metro, estacionDestino ,conversionUltNodo);
            //nodos floyd ruta me da la linea anterior no la linea nueva, hacer funcion para encontrar estacion de linea nueva mismo nombre
            direccionNombreFinal = direcionNombre(metro, estacionDestino, direccionFinal);
            System.out.println("Transbordo de " + metro.get(transbordo).getNombre() + "\nLinea " + metro.get(transbordo).getLinea() +
                    " a linea " + metro.get(estacionDestino).getLinea() + " direccion " + direccionNombreFinal.getNombre());
            destino = buscarRutaNodoInicial(metro.get(estacionDestino), metro,  conversionUltNodo, direccionFinal);
            System.out.println("A llegado a su destino estacion " + metro.get(destino).getNombre() + "!");
            return;
        } else if (nodosFloydRuta.size() == 4) {
            direccionInical = buscarDirecciondeNodoaNodo(metro, metro.indexOf(nodosFloydRuta.get(0)), metro.indexOf(nodosFloydRuta.get(1)));
            transbordo = buscarRutaNodo(metro.get(metro.indexOf(nodosFloydRuta.get(1))), metro, metro.indexOf(nodosFloydRuta.get(0)), direccionInical);
            conversionUltNodo = linearSearchLinea(metro, metro.indexOf(nodosFloydRuta.get(3)),estacionDestino);
            direccionFinal = buscarDirecciondeNodoaNodo(metro,  metro.indexOf(nodosFloydRuta.get(2)), metro.indexOf(nodosFloydRuta.get(3)));
            direccionNombreFinal = direcionNombre(metro, metro.indexOf(nodosFloydRuta.get(3)), direccionFinal);
            System.out.println("Transbordo de " + metro.get(transbordo).getNombre() + "\nLinea " + metro.get(transbordo).getLinea() +
                    " a linea " + nodosFloydRuta.get(3).getLinea() + " direccion " + direccionNombreFinal.getNombre());
            transbordo = buscarRutaNodo(metro.get(metro.indexOf(nodosFloydRuta.get(3))), metro, metro.indexOf(nodosFloydRuta.get(2)), direccionFinal);
            conversionUltNodo = linearSearchLinea(metro, metro.indexOf(nodosFloydRuta.get(3)),estacionDestino);
            direccionFinal = buscarDireccion(metro, estacionDestino ,conversionUltNodo);
            //nodos floyd ruta me da la linea anterior no la linea nueva, hacer funcion para encontrar estacion de linea nueva mismo nombre
            direccionNombreFinal = direcionNombre(metro, estacionDestino, direccionFinal);
            System.out.println("Transbordo de " + metro.get(transbordo).getNombre() + "\nLinea " + metro.get(transbordo).getLinea() +
                    " a linea " + nodosFloydRuta.get(conversionUltNodo).getLinea() + " direccion " + direccionNombreFinal.getNombre());
            destino = buscarRutaNodoInicial(metro.get(estacionDestino), metro, conversionUltNodo, direccionFinal);
            System.out.println("A llegado a su destino estacion " + metro.get(destino).getNombre() + "!");
            return;
        } else if (nodosFloydRuta.size() == 3) {
            direccionInical = buscarDirecciondeNodoaNodo(metro, metro.indexOf(nodosFloydRuta.get(0)), metro.indexOf(nodosFloydRuta.get(1)));
            transbordo = buscarRutaNodo(metro.get(metro.indexOf(nodosFloydRuta.get(1))), metro, metro.indexOf(nodosFloydRuta.get(0)), direccionInical);
            conversionUltNodo = linearSearchLinea(metro, metro.indexOf(nodosFloydRuta.get(2)),estacionDestino);
            direccionFinal = buscarDirecciondeNodoaNodo(metro, conversionUltNodo, estacionDestino);
            direccionNombreFinal = direcionNombre(metro, conversionUltNodo, direccionFinal);
            System.out.println("Transbordo de " + metro.get(transbordo).getNombre() + "\nLinea " + metro.get(transbordo).getLinea() +
                    " a linea " + nodosFloydRuta.get(conversionUltNodo).getLinea() + " direccion " + direccionNombreFinal.getNombre());
            destino = buscarRutaNodoInicial(metro.get(estacionDestino), metro, conversionUltNodo, direccionFinal);
            System.out.println("A llegado a su destino estacion " + metro.get(destino).getNombre() + "!");
            return;
        }
        conversionUltNodo = linearSearchLinea(metro, metro.indexOf(nodosFloydRuta.get(nodosFloydRuta.size() - 2)),estacionDestino);
        direccionFinal = buscarDireccion(metro, estacionDestino, conversionUltNodo);
        destino = buscarRutaNodoInicial(metro.get(estacionDestino), metro, conversionUltNodo, direccionFinal);
        System.out.println("A llegado a su destino estacion " + metro.get(destino).getNombre() + "!");
    }

//    static private int transobordoCorrecto(ArrayList<Estaciones> nodosFloyd, Estaciones estacionAnterios) {
//        for (int i = 0; i < nodosFloyd.size(); i++) {
//
//        }
//    }
    private static Estaciones direcionNombre(ArrayList<Estaciones> metro, int estacionInicial, int direccion) {
        if (direccion == 1) {
            for (int i = estacionInicial; i < metro.size() - 1; i++) {
                if (metro.get(i).getLinea() != metro.get(estacionInicial).getLinea()) {
                    return metro.get(--i);
                }
            }
        }else {
            for (int i = estacionInicial; i < metro.size() - 1; i--) {
                if (metro.get(i).getLinea() != metro.get(estacionInicial).getLinea()) {
                    return metro.get(++i);
                }
            }
        }
        return null;
    }
    private static ArrayList<ArrayList<Estaciones>> compartenNodo(ArrayList<Estaciones> metro, int estacionInicial, int estascionDestino) {
        ArrayList <ArrayList<Estaciones>> resultado = new ArrayList<ArrayList<Estaciones>>();
        resultado = compartenNodoLogica(metro, estacionInicial, estascionDestino);
//        if (resultado[0] == null || resultado[1] == null) {
//            return null;
//        } else if (resultado[0].getNombre().equals(resultado[1].getNombre()))
//            return resultado;
        return resultado;
    }

    private static ArrayList<ArrayList<Estaciones>> compartenNodoLogica(ArrayList<Estaciones> metro,  int estacionInicial, int estascionDestino) {
        ArrayList<Estaciones> resutladosIniciales = new ArrayList<>();
        ArrayList<Estaciones> resultadosDestino = new ArrayList<>();
        ArrayList<ArrayList<Estaciones>> resultadosFinales = new ArrayList<ArrayList<Estaciones>>();
        if (metro.get(estacionInicial).isNode() && metro.get(estascionDestino).isNode()) {
            resutladosIniciales.add(metro.get(estacionInicial));
            resutladosIniciales.add(metro.get(estacionInicial));
            resultadosDestino.add(metro.get(estascionDestino));
            resultadosDestino.add(metro.get(estascionDestino));
            resultadosFinales.add(resutladosIniciales);
            resultadosFinales.add(resultadosDestino);
            return resultadosFinales;
//        } else if (metro.get(estacionInicial).isNode() && !metro.get(estascionDestino).isNode()) {
//            resultadosFinales[0] = metro.get(estacionInicial);
//            resultadosDestino = buscarNodosCercanos(metro, estascionDestino);
//            for (int i = 0; i < resultadosDestino.length -1 ; i++) {
//                if (resultadosDestino[i] == null)
//                    continue;
//                resultadosFinales[1] = resultadosDestino[i];
//            }
//            return resultadosFinales;
//        } else if (metro.get(estascionDestino).isNode() && !metro.get(estacionInicial).isNode()) {
//            resultadosFinales[1] = metro.get(estascionDestino);
//            resultadosDestino = buscarNodosCercanos(metro, estacionInicial);
//            for (int i = 0; i < resultadosDestino.length -1 ; i++) {
//                if (resultadosDestino[i] == null)
//                    continue;
//                resultadosFinales[0] = resultadosDestino[i];
//            }
        } else {
            resutladosIniciales = buscarNodosCercanos(metro, estacionInicial);
            resultadosDestino = buscarNodosCercanos(metro,estascionDestino);
            resultadosFinales.add(resutladosIniciales);
            resultadosFinales.add(resultadosDestino);
//            for (int i = 0; i < resutladosIniciales.length  ; i++) {
//                for (int j = 0; j < resultadosDestino.length ; j++) {
//                    if (resultadosDestino[j] == null || resutladosIniciales[i] == null) {
//                        continue;
//                    }
//                    if (resutladosIniciales[i].getNombre().equals(resultadosDestino[j].getNombre())) {
//                        resultadosFinales[0] = resutladosIniciales[i];
//                        resultadosFinales[1] = resultadosDestino[j];
//                        return resultadosFinales;
//                    }
//                    resultadosFinales[0] = resutladosIniciales[i];
//                    resultadosFinales[1] =  resultadosDestino[j];
//                }
//            }
        }
        return resultadosFinales;
    }

    private static ArrayList<Estaciones> buscarNodosCercanos(ArrayList<Estaciones> metro, int estacionBuscar) {
        ArrayList<Estaciones> resultados = new ArrayList<>();
        for (int i = estacionBuscar; i < metro.size() - 1; i++) {
            if (metro.get(i).getLinea() != metro.get(estacionBuscar).getLinea()) {
                break;
            }
            if (metro.get(i).isNode()) {
                resultados.add(metro.get(i));
                break;
            }
        }
        for (int i = estacionBuscar; i < metro.size() -1; i--) {
            if (metro.get(i).getLinea() != metro.get(estacionBuscar).getLinea()) {
                break;
            }
            if (metro.get(i).isNode()) {
                resultados.add(metro.get(i));
                break;
            }
        }
        return resultados;
    }

    private static int buscarDirecciondeNodoaNodo(ArrayList<Estaciones> metro, int estacionNodoInicial, int estacionNodoDestino) {
        for (int i = estacionNodoInicial; i < metro.size() - 1; i++) {
            if (metro.get(i).getLinea() != metro.get(estacionNodoInicial).getLinea()) {
                break;
            }
            if (metro.get(i).getNombre().equals(metro.get(estacionNodoDestino).getNombre())) {
                return 1;
            }
        }
        for (int i = estacionNodoInicial; i < metro.size() -1; i--) {
            if (metro.get(i).getLinea() != metro.get(estacionNodoInicial).getLinea()) {
                break;
            }
            if (metro.get(i).getNombre().equals(metro.get(estacionNodoDestino).getNombre())) {
                return  -1;
            }
        }
        return 0;
    }

    private static int buscarDireccion(ArrayList<Estaciones> metro, int estacionNodo, int estacionDestino) {
        for (int i = estacionDestino; i < metro.size() - 1; i++) {
            if (metro.get(i).getLinea() != metro.get(estacionDestino).getLinea()) {
                break;
            }
            if (metro.get(i).getNombre().equals(metro.get(estacionNodo).getNombre())) {
                return 1;
            }
        }
        for (int i = estacionDestino; i < metro.size() -1; i--) {
            if (metro.get(i).getLinea() != metro.get(estacionDestino).getLinea()) {
                break;
            }
            if (metro.get(i).getNombre().equals(metro.get(estacionNodo).getNombre())) {
                return  -1;
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Estacion: " +
                "Nombre: " + nombre +
                ", ID: " + ID +
                ", Linea:" + linea;
    }
}
