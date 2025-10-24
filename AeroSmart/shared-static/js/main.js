cat > shared-static/js/main.js << 'EOF'
// JavaScript commun à tous les services AeroSmart

document.addEventListener('DOMContentLoaded', function() {
    // Gestion des messages flash
    initFlashMessages();
    
    // Initialisation des tooltips Bootstrap
    initTooltips();
    
    // Gestion des formulaires
    initForms();
    
    // Formatage des dates
    formatDates();
});

function initFlashMessages() {
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(alert => {
        setTimeout(() => {
            alert.style.opacity = '0';
            setTimeout(() => {
                if (alert.parentElement) {
                    alert.remove();
                }
            }, 300);
        }, 5000);
    });
}

function initTooltips() {
    const tooltipTriggerList = [].slice.call(
        document.querySelectorAll('[data-bs-toggle="tooltip"]')
    );
    tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });
}

function initForms() {
    // Validation des formulaires
    const forms = document.querySelectorAll('form');
    forms.forEach(form => {
        form.addEventListener('submit', function(e) {
            const requiredFields = form.querySelectorAll('[required]');
            let valid = true;
            
            requiredFields.forEach(field => {
                if (!field.value.trim()) {
                    valid = false;
                    field.classList.add('is-invalid');
                } else {
                    field.classList.remove('is-invalid');
                }
            });
            
            if (!valid) {
                e.preventDefault();
                showToast('Veuillez remplir tous les champs obligatoires', 'error');
            }
        });
    });
}

function formatDates() {
    const dateElements = document.querySelectorAll('.date-format');
    dateElements.forEach(el => {
        const date = new Date(el.textContent);
        if (!isNaN(date)) {
            el.textContent = date.toLocaleDateString('fr-FR', {
                year: 'numeric',
                month: 'long',
                day: 'numeric',
                hour: '2-digit',
                minute: '2-digit'
            });
        }
    });
}

// Fonction utilitaire pour formater les prix
function formatPrice(price) {
    return new Intl.NumberFormat('fr-FR', {
        style: 'currency',
        currency: 'EUR'
    }).format(price);
}

// Fonction pour afficher des toasts (notifications)
function showToast(message, type = 'info') {
    const toastContainer = document.getElementById('toast-container') || createToastContainer();
    
    const toast = document.createElement('div');
    toast.className = `toast align-items-center text-bg-${type} border-0`;
    toast.setAttribute('role', 'alert');
    toast.innerHTML = `
        <div class="d-flex">
            <div class="toast-body">${message}</div>
            <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
        </div>
    `;
    
    toastContainer.appendChild(toast);
    const bsToast = new bootstrap.Toast(toast);
    bsToast.show();
    
    toast.addEventListener('hidden.bs.toast', () => {
        toast.remove();
    });
}

function createToastContainer() {
    const container = document.createElement('div');
    container.id = 'toast-container';
    container.className = 'toast-container position-fixed top-0 end-0 p-3';
    container.style.zIndex = '9999';
    document.body.appendChild(container);
    return container;
}

// API utilities
async function apiCall(url, options = {}) {
    try {
        const response = await fetch(url, {
            headers: {
                'Content-Type': 'application/json',
                ...options.headers
            },
            ...options
        });
        
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        return await response.json();
    } catch (error) {
        console.error('API call failed:', error);
        showToast('Erreur de connexion au serveur', 'error');
        throw error;
    }
}

// Gestion de la recherche en temps réel
function initSearch() {
    const searchInputs = document.querySelectorAll('.search-input');
    searchInputs.forEach(input => {
        input.addEventListener('input', debounce(function(e) {
            const searchTerm = e.target.value.toLowerCase();
            const items = document.querySelectorAll('.searchable-item');
            
            items.forEach(item => {
                const text = item.textContent.toLowerCase();
                item.style.display = text.includes(searchTerm) ? '' : 'none';
            });
        }, 300));
    });
}

// Debounce function pour les recherches
function debounce(func, wait) {
    let timeout;
    return function executedFunction(...args) {
        const later = () => {
            clearTimeout(timeout);
            func(...args);
        };
        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
    };
}

// Gestion du compteur de sièges
function initSeatCounter() {
    const seatInputs = document.querySelectorAll('.seat-counter');
    seatInputs.forEach(input => {
        const minusBtn = input.parentElement.querySelector('.btn-minus');
        const plusBtn = input.parentElement.querySelector('.btn-plus');
        
        minusBtn?.addEventListener('click', () => {
            const value = parseInt(input.value) || 0;
            if (value > parseInt(input.min || 1)) {
                input.value = value - 1;
                updatePrice(input);
            }
        });
        
        plusBtn?.addEventListener('click', () => {
            const value = parseInt(input.value) || 0;
            const max = parseInt(input.max || 10);
            if (value < max) {
                input.value = value + 1;
                updatePrice(input);
            }
        });
        
        input.addEventListener('change', () => updatePrice(input));
    });
}

function updatePrice(input) {
    const basePrice = parseFloat(input.dataset.basePrice) || 0;
    const seats = parseInt(input.value) || 1;
    const totalPrice = basePrice * seats;
    
    const priceElement = document.getElementById(input.dataset.priceTarget);
    if (priceElement) {
        priceElement.textContent = formatPrice(totalPrice);
    }
}
EOF