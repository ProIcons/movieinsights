import {TextFormat} from "app/utils/text-format-utils";
import React, {CSSProperties} from "react";

export interface DataTableField {
  key: string,
  label: string,
  _classes?: string[],
  _format?: string,
  _customFormat?: (item: any, index?: number) => JSX.Element;
  _valueSuffix?:string;
  _style?: CSSProperties,
  sorter?: boolean,
  filter?: boolean
}

export const getScopedSlots = (fields: DataTableField[]) : any => {
  return fields.filter((field) => field._format !== undefined || field._customFormat !== undefined).reduce((obj, field) => {
    obj[field.key] = (item) =>
      item[field.key]?
      field._format ? (
        <td>
          <TextFormat
            value={item[field.key]}
            type="number"
            format={field._format}
          />
          {field._valueSuffix || ''}
        </td>
      ) : (
        <>{field._customFormat(item)}</>
      ) : <td></td>;
    return obj;
  }, {});
}
