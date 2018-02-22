import React, { Component } from 'react';
import cx from 'classnames';

import '../../css/components/Button.scss';

export const BUTTON_TYPES = {
    PRIMARY: 'primary',
    SECONDARY: 'secondary'
};

export default class Button extends Component {
    render() {
        const { text, className, type = BUTTON_TYPES.PRIMARY, onClick } = this.props;
        return (
            <div className={cx('Button', `Button-${type}`, className)} onClick={onClick}>
                {text}
            </div>
        );
    }
}