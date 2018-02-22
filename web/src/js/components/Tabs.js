import React, { Component } from 'react';
import _ from 'lodash';
import cx from 'classnames';

import '../../css/components/Tabs.scss';
import Button from "./Button";

export default class Tabs extends Component {
    constructor(props) {
        super(props);

        this.state = {
            activeTab: props.activeTab || 0
        };
    }

    handleTabChange(index) {
        this.setState({
            activeTab: index
        });
    }

    render() {
        const { tabs } = this.props;
        const { activeTab } = this.state;
        let titles = _.map(tabs, 'title');

        return (
            <div className="Tabs">
                <div className="Tabs--TabBar">
                    { titles.map(title => {
                        let index = titles.indexOf(title);

                        return (
                            <Button
                                className={cx("Tabs--TabBar--Tab", {"Tabs--TabBar--Tab-active": index === activeTab })}
                                key={index}
                                onClick={this.handleTabChange.bind(this, index)}
                                text={title}
                             />
                        );
                    } ) }
                </div>
                <div className="Tabs--Body">
                    { tabs[activeTab].body }
                </div>
            </div>
        );
    }
}