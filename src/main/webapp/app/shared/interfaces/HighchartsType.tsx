import Highcharts from "highcharts";
import React from "react";

export interface HighchartsType {
    chart: Highcharts.Chart,
    container: React.RefObject<HTMLDivElement>
}
