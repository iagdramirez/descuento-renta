type DesgloseDescuentos = {
  msg?: string;
  salario: number;
  descuentoAFP: number;
  descuentoISSS: number;
  salarioDescuentoAFPISSS: number;
  descuentoRenta: number;
  salarioNetoMensual: number;
  salarioNetoQuincenal: number;
};

type TablaRetenciones = {
  cuotaFijaUno: number;
  cuotaFijaDos: number;
  cuotaFijaTres: number;
  excesoDeRentaUno: number;
  excesoDeRentaDos: number;
  excesoDeRentaTres: number;
  porcentajeDescuentoRentaUno: number;
  porcentajeDescuentoRentaDos: number;
  porcentajeDescuentoRentaTres: number;
};

function roundNumber(num: number) {
  return Math.round(num * 100) / 100;
}

function calcularSalario(salario: number) {
  const porcentajeDescuentoAFP = 7.25; // 7.25%
  const porcentajeDescuentoISSS = 3.0; // 3.00%
  const tablaRetenciones: TablaRetenciones = {
    cuotaFijaUno: 17.67,
    cuotaFijaDos: 60.0,
    cuotaFijaTres: 288.57,
    excesoDeRentaUno: 472.01,
    excesoDeRentaDos: 895.25,
    excesoDeRentaTres: 2038.11,
    porcentajeDescuentoRentaUno: 0.1,
    porcentajeDescuentoRentaDos: 0.2,
    porcentajeDescuentoRentaTres: 0.3,
  };

  let descuentoAFP: number = roundNumber((salario * porcentajeDescuentoAFP) / 100);
  let descuentoISSS: number = roundNumber((salario * porcentajeDescuentoISSS) / 100);

  let desc_AFP_ISS_Inicial: number = salario - (descuentoAFP + descuentoISSS);

  if (desc_AFP_ISS_Inicial + descuentoAFP >= 1000) descuentoISSS = 30.0;
  if (desc_AFP_ISS_Inicial + descuentoAFP >= 6377.15) descuentoAFP = 398.57;

  const salarioDescuentoAFPISSS = salario - (descuentoAFP + descuentoISSS);
  let res: DesgloseDescuentos = {
    salario,
    descuentoAFP,
    descuentoISSS,
    descuentoRenta: 0,
    salarioDescuentoAFPISSS,
    salarioNetoMensual: salarioDescuentoAFPISSS,
    salarioNetoQuincenal: salarioDescuentoAFPISSS / 2,
  };

  let cuotaFija = 0;
  let excesoDeRenta = 0;
  let porcentajeDescuentoRenta = 0;

  if (
    res.salarioDescuentoAFPISSS >= tablaRetenciones.excesoDeRentaUno &&
    res.salarioDescuentoAFPISSS < tablaRetenciones.excesoDeRentaDos
  ) {
    cuotaFija = tablaRetenciones.cuotaFijaUno;
    excesoDeRenta = tablaRetenciones.excesoDeRentaUno;
    porcentajeDescuentoRenta = tablaRetenciones.porcentajeDescuentoRentaUno;
  } else if (
    res.salarioDescuentoAFPISSS >= tablaRetenciones.excesoDeRentaDos &&
    res.salarioDescuentoAFPISSS < tablaRetenciones.excesoDeRentaTres
  ) {
    cuotaFija = tablaRetenciones.cuotaFijaDos;
    excesoDeRenta = tablaRetenciones.excesoDeRentaDos;
    porcentajeDescuentoRenta = tablaRetenciones.porcentajeDescuentoRentaDos;
  } else if (
    res.salarioDescuentoAFPISSS >= tablaRetenciones.excesoDeRentaTres
  ) {
    cuotaFija = tablaRetenciones.cuotaFijaTres;
    excesoDeRenta = tablaRetenciones.excesoDeRentaTres;
    porcentajeDescuentoRenta = tablaRetenciones.porcentajeDescuentoRentaTres;
  }

  const descRentaSinCF = (res.salarioDescuentoAFPISSS - excesoDeRenta) * porcentajeDescuentoRenta;
  res.descuentoRenta = roundNumber(descRentaSinCF + cuotaFija);

  res.salarioNetoMensual = roundNumber(res.salarioDescuentoAFPISSS - res.descuentoRenta);
  res.salarioNetoQuincenal = roundNumber(res.salarioNetoMensual / 2);

  return res;
}

const desglose = calcularSalario(1600);
console.log(desglose);
