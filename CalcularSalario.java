import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

class CalcularSalario {

  public static void main(String[] args) {
    // Obtener el salario mediante consola
    double salario = obtenerSalario();

    // Establecer porcentajes de descuento AFP e ISSS
    double porcentajeDescuentoAFP = 0.0725; // 7.25%
    double porcentajeDescuentoISSS = 0.03; // 3.00%

    // Tramos de decuento de renta
    double cuotaFijaUno = 17.67;
    double excesoDeRentaUno = 472.00;
    double porcentajeDescuentoRentaUno = 0.1; // 10%

    double cuotaFijaDos = 60.0;
    double excesoDeRentaDos = 895.24;
    double porcentajeDescuentoRentaDos = 0.2; // 20%

    double cuotaFijaTres = 288.57;
    double excesoDeRentaTres = 2038.10;
    double porcentajeDescuentoRentaTres = 0.3; // 30%

    // Calcular el descuento de AFP e ISSS por el porcentaje
    double descuentoAFP = salario * porcentajeDescuentoAFP;
    double descuentoISSS = salario * porcentajeDescuentoISSS;

    //? Se limita el descuento del AFP a $398.57 cuando el salario es mayor a $6377.15?
    // if (salario >= 6377.15) descuentoAFP = 398.57;

    // Limitar descuento del ISS a $30 si el salario es mayor a $1000
    if (salario >= 1000) descuentoISSS = 30.0;

    // Calcular el salario menos los descuentos del AFP e ISSS
    double salarioDescuentoAFPISSS = salario - (descuentoAFP + descuentoISSS);

    // Calcular Salario Neto Mensual y Quincenal
    double salarioNetoMensual = salarioDescuentoAFPISSS;
    double salarioNetoQuincenal = salarioDescuentoAFPISSS / 2;

    // Inicializar los tramos que se van a usar
    double cuotaFija = 0;
    double excesoDeRenta = 0;
    double porcentajeDescuentoRenta = 0;

    //! Si el salario menos los descuentos del AFP e ISSS no se encuentra en niguno
    //! de los tramos II, III & IV, se intuye que estará en el Tramo I
    //! por lo cuál no se le aplica ningún descuento de renta.

    //* Verificar si el salario (menos los descuentos del AFP e ISSS)
    //* se encuentra en el Tramo II
    if (
      salarioDescuentoAFPISSS > excesoDeRentaUno &&
      salarioDescuentoAFPISSS <= excesoDeRentaDos
    ) {
      cuotaFija = cuotaFijaUno;
      excesoDeRenta = excesoDeRentaUno;
      porcentajeDescuentoRenta = porcentajeDescuentoRentaUno;
    }
    //* Verificar si el salario (menos los descuentos del AFP e ISSS)
    //* se encuentra en el Tramo III
    else if (
      salarioDescuentoAFPISSS > excesoDeRentaDos &&
      salarioDescuentoAFPISSS <= excesoDeRentaTres
    ) {
      cuotaFija = cuotaFijaDos;
      excesoDeRenta = excesoDeRentaDos;
      porcentajeDescuentoRenta = porcentajeDescuentoRentaDos;
    }
    //* Verificar si el salario (menos los descuentos del AFP e ISSS)
    //* se encuentra en el Tramo IV
    else if (salarioDescuentoAFPISSS > excesoDeRentaTres) {
      cuotaFija = cuotaFijaTres;
      excesoDeRenta = excesoDeRentaTres;
      porcentajeDescuentoRenta = porcentajeDescuentoRentaTres;
    }

    // Se calcula el descuento de renta sin tomar en cuenta la cuota fija según el tramo
    double descRentaSinCF =
      (salarioDescuentoAFPISSS - excesoDeRenta) * porcentajeDescuentoRenta;

    // Se le agrega la cuota fija al descuento de renta
    double descuentoRenta = descRentaSinCF + cuotaFija;

    // Se vuelve a calcular el salario neto mensual y quincenal
    salarioNetoMensual = salarioDescuentoAFPISSS - descuentoRenta;
    salarioNetoQuincenal = salarioNetoMensual / 2;

    // Declarando el formato de los montos finales
    DecimalFormat df = new DecimalFormat("#.##");

    // Imprimir headers de la tabla
    System.out.println("-------------------------");
    System.out.println("Tipo                    |  Mensual - Quincenal");
    System.out.println("-------------------------");

    // Imprimir Salario
    System.out.print("Salario                 |  ");
    System.out.print("$" + df.format(salario));
    System.out.print(" - ");
    System.out.println("$" + df.format(salario / 2));
    System.out.println("-------------------------");

    // Imprimir Descuento AFP
    System.out.print("Descuento AFP           |  ");
    System.out.print("$" + df.format(descuentoAFP));
    System.out.print(" - ");
    System.out.println("$" + df.format(descuentoAFP / 2));
    System.out.println("-------------------------");

    // Imprimir Descuento ISSS
    System.out.print("Descuento ISSS          |  ");
    System.out.print("$" + df.format(descuentoISSS));
    System.out.print(" - ");
    System.out.println("$" + df.format(descuentoISSS / 2));
    System.out.println("-------------------------");

    // Salario con descuento del AFP y del ISSS
    System.out.print("Salario sin AFP e ISSS  |  ");
    System.out.print("$" + df.format(salarioDescuentoAFPISSS));
    System.out.print(" - ");
    System.out.println("$" + df.format(salarioDescuentoAFPISSS / 2));
    System.out.println("-------------------------");

    // Imprimir Descuento de Renta
    System.out.print("Descuento Renta:        |  ");
    System.out.print("$" + df.format(descuentoRenta));
    System.out.print(" - ");
    System.out.println("$" + df.format(descuentoRenta / 2));
    System.out.println("-------------------------");

    // Imprimir Salario Neto
    System.out.print("Salario Neto:           |  ");
    System.out.print("$" + df.format(salarioNetoMensual));
    System.out.print(" - ");
    System.out.println("$" + df.format(salarioNetoQuincenal));
    System.out.println("-------------------------");
  }

  // Función para pedir el salario al usuario
  public static double obtenerSalario() {
    // Declarar el Scanner
    Scanner scanner = new Scanner(System.in);

    // Inicializar el salario
    double salario = 0;

    // Inicializar un bucle donde se compruebe que ingrese sea válido
    boolean salarioValido = false;
    while (!salarioValido) {
      try {
        // Captura el salario ingresado
        System.out.print("Ingrese su salario: $");
        salario = scanner.nextDouble();

        // Verifica que el salario ingresado sea mayor a $0
        if (salario > 0) salarioValido = true;
        // Si el salario es menor a $0 arroja un mensaje personalizado
        else {
          System.out.println();
          System.out.println("El salario debe ser mayor a $0");
        }
      } catch (InputMismatchException e) {
        // Si el salario ingresado contiene caractéres inválidos arroja otro mensaje personalizado
        System.out.println();
        System.out.println("El dato ingresado no es válido!");
        scanner.next();
      }
      System.out.println();
    }

    // Cerrar scanner
    scanner.close();

    // Devolver el salario
    return salario;
  }
}
