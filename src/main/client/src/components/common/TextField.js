import PropTypes from 'prop-types';

import "./TextField.css";

const TextField = ({placeholder, type, name, value, onChange, error, disabled, width, margin}) => {
    return (
        <div id="textfield-container" style={{width, margin}}>
            <label htmlFor={placeholder}>
                <input className={error ? "invalid-input" : ""} type={type} name={name} placeholder=" " value={value} onChange={onChange} disabled={disabled}/>
                <span className={error ? "invalid-input" : ""}>{placeholder}</span>
            </label>
            <div className="error-div">
                <span className="error-span">{error}</span>
            </div>
        </div>
    )
}

TextField.propTypes = {
    name: PropTypes.string.isRequired,
    placeholder: PropTypes.string.isRequired,
    value: PropTypes.string.isRequired,
    onChange: PropTypes.func.isRequired,
    error: PropTypes.string,
    type: PropTypes.string,
    disabled: PropTypes.bool,
    width: PropTypes.string
}

TextField.defaultProps = {
    type: 'text',
    width: "100%",
    margin: "15px 0px 0px 0px"
}

export default TextField
