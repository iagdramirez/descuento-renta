import java.util.Scanner;
import java.text.DecimalFormat;

class HelloWorld {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    DecimalFormat df = new DecimalFormat("#.##");

    System.out.print("Ingrese su salario: $");
    double salario = scanner.nextDouble();
    scanner.close();

    double porcentajeDescuentoAFP = 0.0725;
    double porcentajeDescuentoISSS = 0.03;

    double cuotaFijaUno = 17.67;
    double cuotaFijaDos = 60.0;
    double cuotaFijaTres = 288.57;
    double excesoDeRentaUno = 472.01;
    double excesoDeRentaDos = 895.25;
    double excesoDeRentaTres = 2038.11;
    double porcentajeDescuentoRentaUno = 0.1;
    double porcentajeDescuentoRentaDos = 0.2;
    double porcentajeDescuentoRentaTres = 0.3;

    double descuentoAFP = salario * porcentajeDescuentoAFP;
    double descuentoISSS = salario * porcentajeDescuentoISSS;

    //? Se limita el descuento del AFP a $398.57 cuando el salario es mayor a $6377.15?
    // if (salario >= 6377.15) descuentoAFP = 398.57;
    if (salario >= 1000)
      descuentoISSS = 30.0;

    double salarioDescuentoAFPISSS = salario - (descuentoAFP + descuentoISSS);

    double salarioNetoMensual = salarioDescuentoAFPISSS;
    double salarioNetoQuincenal = salarioDescuentoAFPISSS / 2;

    double cuotaFija = 0;
    double excesoDeRenta = 0;
    double porcentajeDescuentoRenta = 0;

    if (salarioDescuentoAFPISSS >= excesoDeRentaUno && salarioDescuentoAFPISSS < excesoDeRentaDos) {
      cuotaFija = cuotaFijaUno;
      excesoDeRenta = excesoDeRentaUno;
      porcentajeDescuentoRenta = porcentajeDescuentoRentaUno;
    } else if (salarioDescuentoAFPISSS >= excesoDeRentaDos && salarioDescuentoAFPISSS < excesoDeRentaTres) {
      cuotaFija = cuotaFijaDos;
      excesoDeRenta = excesoDeRentaDos;
      porcentajeDescuentoRenta = porcentajeDescuentoRentaDos;
    } else if (salarioDescuentoAFPISSS >= excesoDeRentaTres) {
      cuotaFija = cuotaFijaTres;
      excesoDeRenta = excesoDeRentaTres;
      porcentajeDescuentoRenta = porcentajeDescuentoRentaTres;
    }

    double descRentaSinCF = (salarioDescuentoAFPISSS - excesoDeRenta) * porcentajeDescuentoRenta;
    double descuentoRenta = descRentaSinCF + cuotaFija;

    salarioNetoMensual = salarioDescuentoAFPISSS - descuentoRenta;
    salarioNetoQuincenal = salarioNetoMensual / 2;

    System.out.println(df.format(salario) + " - " + df.format(salario / 2));
    System.out.println("----------------------");
    System.out.println(df.format(descuentoAFP) + " - " + df.format(descuentoAFP / 2));
    System.out.println("----------------------");
    System.out.println(df.format(descuentoISSS) + " - " + df.format(descuentoISSS / 2));
    System.out.println("----------------------");
    System.out.println(df.format(salarioDescuentoAFPISSS) + " - " + df.format(salarioDescuentoAFPISSS / 2));
    System.out.println("----------------------");
    System.out.println(df.format(descuentoRenta) + " - " + df.format(descuentoRenta / 2));
    System.out.println("----------------------");
    System.out.println(df.format(salarioNetoMensual) + " - " + df.format(salarioNetoQuincenal));
  }
}
