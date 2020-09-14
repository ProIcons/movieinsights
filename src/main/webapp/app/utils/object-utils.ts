import { deepObjectsMerge } from '@coreui/utils/src';

export { default as deepEqual } from 'fast-deep-equal';
export * from '@coreui/utils/src';

export const normalizeText = (text: string): string => {
  let _text = text;
  do {
    _text = _text
      ?.toLowerCase()
      .replace(' ', '-')
      .replace(/[&/\\#,+()$!~%.'":*?<>{}[\]]/g, '');
  } while (_text.includes(' '));
  return _text;
};

export const merge = (target, source) => {
  if (source) {
    return deepObjectsMerge(target, source);
  }
  return source;
};
