import React from 'react'
import SimpleBarChart from '../../widgets/charts/SimpleBarChart'
import GenericWidgetLoadingSpinner from "../../widgets/GenericWidgetLoadingSpinner";

export default function DNSStatisticsChart (props) {

  const data = props.data;

  // Optional.
  const conversion = props.conversion;
  const valueType = props.valueType;

  const formatData = () => {
    const result = {}

    Object.keys(data).sort().forEach(function (key) {
      if (conversion) {
        result[key] = conversion(data[key])
      } else {
        result[key] = data[key]
      }
    })

    return result
  }

  if (data === null || data === undefined) {
    return <GenericWidgetLoadingSpinner height={150} />
  }

  return <SimpleBarChart
        height={150}
        lineWidth={1}
        customMarginLeft={45}
        data={formatData()}
        ticksuffix={valueType ? ' ' + valueType : undefined}
        tickformat={'.2~f'}
    />
}