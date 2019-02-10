// ESLint configuration
// http://eslint.org/docs/user-guide/configuring
module.exports = {
  parser: 'babel-eslint',

  extends: ['airbnb', 'prettier', 'prettier/react'],

  plugins: ['prettier'],

  globals: {
    __DEV__: true,
  },

  env: {
    browser: true,
    jest: true,
  },

  rules: {
    // Forbid the use of extraneous packages
    'import/no-extraneous-dependencies': ['error', { packageDir: '.' }],

    // Recommend not to leave any console.log in your code
    // Use console.error, console.warn and console.info instead
    'no-console': [
      'error',
      {
        allow: ['warn', 'error', 'info'],
      },
    ],

    // Allow .js files to use JSX syntax
    'react/jsx-filename-extension': ['error', { extensions: ['.js', '.jsx'] }],

    // ESLint plugin for prettier formatting
    'prettier/prettier': 'warn',
  },

  settings: {
    'import/resolver': {
      node: {
        moduleDirectory: ['node_modules', 'src'],
      },
    },
  },
};
