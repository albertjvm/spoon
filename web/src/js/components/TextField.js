import React, { Component } from 'react';
import PropTypes from 'prop-types';
import cx from 'classnames';
import '../../css/components/TextField.scss';

export default class TextField extends Component {
    render() {
        const { label, value, type, className, onChange } = this.props;

        return (
            <div className={cx('TextField', className)}>
                <input placeholder={label} type={type || "text"} value={value} onChange={onChange} />
            </div>
        );
    }
}

TextField.propTypes = {
    label: PropTypes.string,
    value: PropTypes.string,
    type: PropTypes.string,
    className: PropTypes.string,
    onChange: PropTypes.func,
};