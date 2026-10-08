/**
 * NovaBank Web Client Scripts
 * Incorporates Experiments 8, 9, 10, 11
 */

// ==========================================
// Exp 9: Dynamic Background Color Changer
// ==========================================
function startBgColorChanger() {
    const colors = [
        '#0b0f19', // Default dark blue
        '#0f172a', // Slate
        '#18181b', // Zinc
        '#171717', // Neutral
        '#111827'  // Gray
    ];
    let idx = 0;
    
    // Change background every 10 seconds (subtle)
    setInterval(() => {
        idx = (idx + 1) % colors.length;
        document.body.style.backgroundColor = colors[idx];
    }, 10000);
}

// ==========================================
// Exp 10: Session Stopwatch (Timer)
// ==========================================
function startSessionTimer(loginTimeMillis, timeoutSeconds) {
    const timerDisplay = document.getElementById('session-timer');
    if (!timerDisplay) return;

    const endTime = loginTimeMillis + (timeoutSeconds * 1000);

    const updateTimer = () => {
        const now = Date.now();
        const remaining = Math.max(0, endTime - now);

        if (remaining === 0) {
            if (typeof interval !== 'undefined') clearInterval(interval);
            window.location.href = 'login?action=logout&reason=timeout';
            return;
        }

        const mins = Math.floor(remaining / 60000);
        const secs = Math.floor((remaining % 60000) / 1000);
        
        timerDisplay.textContent = `${mins.toString().padStart(2, '0')}:${secs.toString().padStart(2, '0')}`;

        if (remaining < 60000) { // Less than 1 min
            timerDisplay.className = 'session-timer timer-danger';
        } else if (remaining < 300000) { // Less than 5 mins
            timerDisplay.className = 'session-timer timer-warning';
        }
    };

    updateTimer();
    const interval = setInterval(updateTimer, 1000);
}

// ==========================================
// Exp 8: Email Validation
// ==========================================
function setupEmailValidation() {
    const emailInput = document.getElementById('email');
    if (!emailInput) return;

    const ruleAt = document.getElementById('rule-at');
    const ruleDot = document.getElementById('rule-dot');
    const rulePos = document.getElementById('rule-pos');
    const form = document.getElementById('create-form');

    const checkEmail = (email) => {
        const atPos = email.indexOf('@');
        const lastAtPos = email.lastIndexOf('@');
        const dotPos = email.lastIndexOf('.');

        const hasAt = atPos !== -1;
        const hasDot = hasAt && dotPos > atPos;
        const positionsOk = hasAt && hasDot
            && atPos > 0
            && atPos === lastAtPos
            && dotPos > atPos + 1
            && dotPos < email.length - 1
            && !email.includes(' ');

        return { hasAt, hasDot, positionsOk, valid: positionsOk };
    };

    const updateRules = (state, val) => {
        const set = (el, ok) => {
            if (el) {
                el.classList.toggle('ok', val !== '' && ok);
                el.classList.toggle('bad', val !== '' && !ok);
            }
        };
        set(ruleAt, state.hasAt);
        set(ruleDot, state.hasDot);
        set(rulePos, state.positionsOk);
    };

    emailInput.addEventListener('input', () => {
        const val = emailInput.value.trim();
        updateRules(checkEmail(val), val);
        emailInput.classList.remove('valid', 'invalid');
    });

    if (form) {
        form.addEventListener('submit', (e) => {
            const val = emailInput.value.trim();
            if (val === '') return; // Let HTML5 required handle it

            const state = checkEmail(val);
            updateRules(state, val);

            if (!state.valid) {
                e.preventDefault();
                emailInput.classList.add('invalid');
                alert("Invalid email format. Please follow the rules.");
            } else {
                emailInput.classList.add('valid');
            }
        });
    }
}

// Initialize when DOM is ready
document.addEventListener('DOMContentLoaded', () => {
    startBgColorChanger();
    setupEmailValidation();
    // Note: startSessionTimer is called from dashboard.jsp using server-side values
});
