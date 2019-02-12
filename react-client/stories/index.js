import React from 'react';

import { storiesOf } from '@storybook/react';
import { action } from '@storybook/addon-actions';
import { linkTo } from '@storybook/addon-links';

import { Button, Welcome } from '@storybook/react/demo';
import ProjectTile from '../src/ProjectTile';

storiesOf('Welcome', module).add('to Storybook', () => (
  <Welcome showApp={linkTo('Button')} />
));

storiesOf('Button', module)
  .add('with text', () => (
    <Button onClick={action('clicked')}>Hello Button</Button>
  ))
  .add('with some emoji', () => (
    <Button onClick={action('clicked')}>😀 😎 👍 💯</Button>
  ));

const data = [
  {
    id: 1,
    projectName: 'test',
    projectIdentifier: 'TEST',
    description: 'test description',
    createdAt: '14-12-2019',
  },
];
storiesOf('project tile', module).add('with text', () => (
  <ProjectTile data={data} isMenuOpen={true} />
));
