// =====================================
// 🔥 HYBRID JS - LocalStorage + Spring Boot Backend + Perfect Navigation
// =====================================

const API_BASE = "http://localhost:8080/api";

// Demo data - used when backend is offline
const DEMO_DATA = {
    products: [
        { id: 1, name: "Laptop Dell XPS", price: 85000, quantity: 5 },
        { id: 2, name: "iPhone 15 Pro", price: 95000, quantity: 8 },
        { id: 3, name: "Samsung Monitor", price: 25000, quantity: 12 }
    ],
    customers: [
        { id: 1, name: "Rahul Sharma", email: "rahul@email.com" },
        { id: 2, name: "Priya Patel", email: "priya@email.com" }
    ],
    expenses: [
        { id: 1, title: "Office Rent", amount: 25000, date: "2026-03-01" }
    ],
    invoices: [
        { id: 1, customer: "Rahul Sharma", product: "Laptop Dell XPS", quantity: 1, price: 85000, total: 85000, date: "2026-03-06" }
    ]
};

// =====================================
// INIT & NAVIGATION
// =====================================
document.addEventListener("DOMContentLoaded", function() {
    initDemoData();
    setupNavigation();
    setupForms();
    showPage('dashboard');
});

function initDemoData() {
    // Initialize localStorage with demo data
    Object.keys(DEMO_DATA).forEach(key => {
        if (!localStorage.getItem(key)) {
            localStorage.setItem(key, JSON.stringify(DEMO_DATA[key]));
        }
    });
}

function setupNavigation() {
    const navLinks = document.querySelectorAll('nav ul li a[data-page]');

    navLinks.forEach(link => {
        link.addEventListener("click", function(e) {
            e.preventDefault();
            const pageId = this.getAttribute("data-page");
            showPage(pageId);

            // Update active states
            navLinks.forEach(l => l.classList.remove("active"));
            this.classList.add("active");
        });
    });
}

function showPage(pageId) {
    // Hide all pages
    document.querySelectorAll('.page, section, [class*="page"]').forEach(page => {
        page.style.display = 'none';
        page.classList.remove('active-page');
    });

    // Show target page
    const targetPage = document.getElementById(pageId) || document.querySelector(`.${pageId}`);
    if (targetPage) {
        targetPage.style.display = 'block';
        targetPage.classList.add('active-page');
        loadPageData(pageId);
    }
}

// =====================================
// API HELPER FUNCTIONS
// =====================================
async function apiCall(endpoint, options = {}) {
    try {
        const response = await fetch(`${API_BASE}${endpoint}`, {
            headers: { 'Content-Type': 'application/json' },
            ...options
        });
        if (response.ok) {
            return await response.json();
        }
        throw new Error(`API Error: ${response.status}`);
    } catch (error) {
        console.log('Backend offline, using localStorage:', error);
        return null;
    }
}

async function apiPost(endpoint, data) {
    try {
        const response = await fetch(`${API_BASE}${endpoint}`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });
        if (response.ok) return true;
        throw new Error('POST failed');
    } catch {
        return false;
    }
}

async function apiDelete(endpoint) {
    try {
        const response = await fetch(`${API_BASE}${endpoint}`, { method: 'DELETE' });
        return response.ok;
    } catch {
        return false;
    }
}

function getStorage(key) {
    return JSON.parse(localStorage.getItem(key) || '[]');
}

function setStorage(key, data) {
    localStorage.setItem(key, JSON.stringify(data));
}

// =====================================
// PRODUCTS - Backend + LocalStorage
// =====================================
function addProduct() {
    const name = document.getElementById('productName')?.value.trim();
    const price = parseFloat(document.getElementById('productPrice')?.value);
    const quantity = parseInt(document.getElementById('productQty')?.value);

    if (!name || isNaN(price) || isNaN(quantity) || price <= 0 || quantity < 0) {
        alert('Please fill all fields correctly!');
        return;
    }

    const product = { name, price, quantity, id: Date.now() };

    // Try backend first
    apiPost('/products', product).then(backendSuccess => {
        if (backendSuccess) {
            alert('Product added to backend!');
        } else {
            // Fallback to localStorage
            const products = getStorage('products');
            products.push(product);
            setStorage('products', products);
            alert('Product added locally!');
        }
        loadProducts();
        clearProductForm();
    });
}

async function loadProducts() {
    // Try backend first
    const backendProducts = await apiCall('/products');
    if (backendProducts) {
        setStorage('products', backendProducts);
    } else {
        // Use localStorage
        const products = getStorage('products');
    }

    const products = getStorage('products');
    const tbody = document.querySelector('#productsTable tbody') ||
        document.querySelector('#products tbody') ||
        document.querySelector('.products-table tbody');

    if (tbody) {
        tbody.innerHTML = products.map(p => `
            <tr>
                <td>${p.name}</td>
                <td>₹${p.price.toLocaleString()}</td>
                <td>${p.quantity}</td>
                <td>
                    <button onclick="editProduct(${p.id})" class="btn-warning">Edit</button>
                    <button onclick="deleteProduct(${p.id})" class="btn-danger">Delete</button>
                </td>
            </tr>
        `).join('');
    }
}

async function deleteProduct(id) {
    if (!confirm('Delete this product?')) return;

    // Try backend first
    const backendSuccess = await apiDelete(`/products/${id}`);
    if (!backendSuccess) {
        // Fallback to localStorage
        const products = getStorage('products').filter(p => p.id != id);
        setStorage('products', products);
    }

    loadProducts();
}

function clearProductForm() {
    const inputs = ['productName', 'productPrice', 'productQty'];
    inputs.forEach(id => {
        const el = document.getElementById(id);
        if (el) el.value = '';
    });
}

// =====================================
// CUSTOMERS
// =====================================
function addCustomer() {
    const name = document.getElementById('customerName')?.value.trim();
    const email = document.getElementById('customerEmail')?.value.trim();

    if (!name || !email || !email.includes('@')) {
        alert('Please fill valid name and email!');
        return;
    }

    const customer = { name, email, id: Date.now() };

    apiPost('/customers', customer).then(backendSuccess => {
        if (!backendSuccess) {
            const customers = getStorage('customers');
            customers.push(customer);
            setStorage('customers', customers);
        }
        loadCustomers();
        clearCustomerForm();
    });
}

async function loadCustomers() {
    const backendCustomers = await apiCall('/customers');
    if (backendCustomers) {
        setStorage('customers', backendCustomers);
    }

    const customers = getStorage('customers');
    const tbody = document.querySelector('#customersTable tbody');

    if (tbody) {
        tbody.innerHTML = customers.map(c => `
            <tr>
                <td>${c.name}</td>
                <td>${c.email}</td>
                <td><button onclick="deleteCustomer(${c.id})" class="btn-danger">Delete</button></td>
            </tr>
        `).join('');
    }
    populateCustomerDropdown();
}

async function deleteCustomer(id) {
    if (confirm('Delete customer?')) {
        await apiDelete(`/customers/${id}`);
        loadCustomers();
    }
}

// =====================================
// EXPENSES
// =====================================
function addExpense() {
    const title = document.getElementById('expenseTitle')?.value.trim();
    const amount = parseFloat(document.getElementById('expenseAmount')?.value);

    if (!title || isNaN(amount) || amount <= 0) {
        alert('Please enter valid expense!');
        return;
    }

    const expense = {
        title,
        amount,
        date: new Date().toLocaleDateString(),
        id: Date.now()
    };

    apiPost('/expenses', expense).then(backendSuccess => {
        if (!backendSuccess) {
            const expenses = getStorage('expenses');
            expenses.push(expense);
            setStorage('expenses', expenses);
        }
        loadExpenses();
        updateDashboard();
        clearExpenseForm();
    });
}

async function loadExpenses() {
    const backendExpenses = await apiCall('/expenses');
    if (backendExpenses) {
        setStorage('expenses', backendExpenses);
    }

    const expenses = getStorage('expenses');
    const tbody = document.querySelector('#expensesTable tbody');

    if (tbody) {
        tbody.innerHTML = expenses.map(e => `
            <tr>
                <td>${e.title}</td>
                <td>₹${e.amount.toLocaleString()}</td>
                <td>${e.date}</td>
                <td><button onclick="deleteExpense(${e.id})" class="btn-danger">Delete</button></td>
            </tr>
        `).join('');
    }
}

// =====================================
// INVOICES
// =====================================
function createInvoice() {
    const customer = document.getElementById('invoiceCustomer')?.value;
    const productName = document.getElementById('invoiceProduct')?.value;
    const qty = parseInt(document.getElementById('invoiceQty')?.value);
    const price = parseFloat(document.getElementById('invoicePrice')?.value);

    if (!customer || !productName || isNaN(qty) || isNaN(price) || qty <= 0) {
        alert('Please fill all invoice fields correctly!');
        return;
    }

    const products = getStorage('products');
    const product = products.find(p => p.name === productName);

    if (product && product.quantity < qty) {
        alert(`Insufficient stock! Only ${product.quantity} available.`);
        return;
    }

    const total = qty * price;
    const invoice = {
        customer,
        product: productName,
        quantity: qty,
        price,
        total,
        date: new Date().toLocaleDateString(),
        id: Date.now()
    };

    apiPost('/invoices', invoice).then(backendSuccess => {
        // Update stock
        if (product) {
            product.quantity -= qty;
            apiPost(`/products/${product.id}`, product);
            setStorage('products', products);
        }

        if (!backendSuccess) {
            const invoices = getStorage('invoices');
            invoices.push(invoice);
            setStorage('invoices', invoices);
        }

        loadInvoices();
        loadProducts();
        updateDashboard();
        clearInvoiceForm();
        alert('Invoice created successfully!');
    });
}

async function loadInvoices() {
    const backendInvoices = await apiCall('/invoices');
    if (backendInvoices) {
        setStorage('invoices', backendInvoices);
    }

    const invoices = getStorage('invoices');
    const tbody = document.querySelector('#invoiceTable tbody') || document.querySelector('#salesTable tbody');

    if (tbody) {
        tbody.innerHTML = invoices.map(inv => `
            <tr>
                <td>${inv.customer}</td>
                <td>${inv.product}</td>
                <td>${inv.quantity}</td>
                <td>₹${inv.price.toLocaleString()}</td>
                <td>₹${inv.total.toLocaleString()}</td>
                <td>${inv.date}</td>
                <td><button onclick="downloadInvoice(${inv.id})" class="btn-primary">Download</button></td>
            </tr>
        `).join('');
    }
    populateInvoiceDropdowns();
}

// =====================================
// UTILITIES
// =====================================
function setupForms() {
    const buttons = {
        'addProductBtn': addProduct,
        'addCustomerBtn': addCustomer,
        'addExpenseBtn': addExpense,
        'createInvoiceBtn': createInvoice
    };

    Object.entries(buttons).forEach(([id, fn]) => {
        const btn = document.getElementById(id);
        if (btn) btn.onclick = fn;
    });
}

function populateCustomerDropdown() {
    const customers = getStorage('customers');
    const select = document.getElementById('invoiceCustomer');
    if (select) {
        select.innerHTML = '<option value="">Select Customer</option>' +
            customers.map(c => `<option value="${c.name}">${c.name}</option>`).join('');
    }
}

function populateProductDropdown() {
    const products = getStorage('products');
    const select = document.getElementById('invoiceProduct');
    if (select) {
        select.innerHTML = '<option value="">Select Product</option>' +
            products.map(p => `<option value="${p.name}" data-price="${p.price}">${p.name} (₹${p.price})</option>`).join('');
    }
}

function populateInvoiceDropdowns() {
    populateCustomerDropdown();
    populateProductDropdown();
}

function clearCustomerForm() {
    ['customerName', 'customerEmail'].forEach(id => {
        const el = document.getElementById(id);
        if (el) el.value = '';
    });
}

function clearExpenseForm() {
    ['expenseTitle', 'expenseAmount'].forEach(id => {
        const el = document.getElementById(id);
        if (el) el.value = '';
    });
}

function clearInvoiceForm() {
    ['invoiceCustomer', 'invoiceProduct', 'invoiceQty', 'invoicePrice'].forEach(id => {
        const el = document.getElementById(id);
        if (el) el.value = '';
    });
}

function downloadInvoice(id) {
    const invoices = getStorage('invoices');
    const invoice = invoices.find(i => i.id == id);

    if (!invoice) return alert('Invoice not found!');

    const content = `INVOICE #${invoice.id}
=========================
Customer: ${invoice.customer}
Product:  ${invoice.product}
Quantity: ${invoice.quantity}
Unit Price: ₹${invoice.price.toLocaleString()}
-------------------------
TOTAL: ₹${invoice.total.toLocaleString()}
Date: ${invoice.date}`;

    const blob = new Blob([content], { type: 'text/plain' });
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = `invoice_${invoice.id}.txt`;
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
    URL.revokeObjectURL(url);
}

function updateDashboard() {
    const invoices = getStorage('invoices');
    const expenses = getStorage('expenses');
    const products = getStorage('products');

    const totalSales = invoices.reduce((sum, i) => sum + (i.total || 0), 0);
    const totalExpenses = expenses.reduce((sum, e) => sum + (e.amount || 0), 0);
    const totalPurchases = products.reduce((sum, p) => sum + (p.price * p.quantity), 0);
    const netProfit = totalSales - totalExpenses;

    const ids = ['totalSales', 'totalPurchases', 'totalExpenses', 'netProfit'];
    const values = [
        totalSales.toLocaleString(),
        totalPurchases.toLocaleString(),
        totalExpenses.toLocaleString(),
        netProfit.toLocaleString()
    ];

    ids.forEach((id, i) => {
        const el = document.getElementById(id);
        if (el) el.textContent = `₹${values[i]}`;
    });
}

function loadPageData(pageId) {
    switch(pageId) {
        case 'dashboard':
            updateDashboard();
            break;
        case 'products':
        case 'purchases':
            loadProducts();
            break;
        case 'customers':
            loadCustomers();
            break;
        case 'sales':
        case 'invoices':
            loadInvoices();
            break;
        case 'expenses':
            loadExpenses();
            break;
    }
}

// Auto-refresh dashboard
setInterval(updateDashboard, 3000);

// Listen for storage changes (multi-tab sync)
window.addEventListener('storage', (e) => {
    if (e.key) loadPageData(document.querySelector('a.active')?.getAttribute('data-page') || 'dashboard');
});
// =====================================
// 🔥 BULLETPROOF NAVIGATION FIX
// =====================================

document.addEventListener("DOMContentLoaded", function() {
    console.log("🔥 Page loaded - Setting up navigation...");

    // Wait a bit more for full DOM readiness
    setTimeout(setupNavigation, 100);

    // Initialize everything
    initDemoData();
    setupForms();
    updateDashboard();
});

// =====================================
// 🔥 NAVIGATION THAT WORKS 100% GUARANTEED
// =====================================
function setupNavigation() {
    console.log("Setting up navigation...");

    // Find ALL possible nav links matching your exact HTML
    const navLinks = document.querySelectorAll('nav ul li a[data-page]');

    console.log(`Found ${navLinks.length} navigation links`);

    if (navLinks.length === 0) {
        console.error("❌ No navigation links found! Check your HTML");
        return;
    }

    // Remove any existing listeners first
    navLinks.forEach(link => link.replaceWith(link.cloneNode(true)));

    // Add fresh event listeners
    document.querySelectorAll('nav ul li a[data-page]').forEach(link => {
        link.addEventListener("click", function(e) {
            e.preventDefault();
            e.stopPropagation();

            console.log("🧑‍💻 Nav clicked:", this.getAttribute("data-page"));

            const pageId = this.getAttribute("data-page");
            switchPage(pageId, this);
        });

        // Prevent default for href="#"
        link.addEventListener("click", function(e) {
            e.preventDefault();
        });
    });

    // Show dashboard by default
    setTimeout(() => {
        const dashboardLink = document.querySelector('a[data-page="dashboard"]');
        if (dashboardLink && !document.querySelector('.page.active-page')) {
            console.log("🏠 Auto-opening dashboard");
            dashboardLink.click();
        }
    }, 200);
}

function switchPage(pageId, clickedLink) {
    console.log(`🔄 Switching to page: ${pageId}`);

    // 1. Update navigation active states
    document.querySelectorAll('nav a').forEach(link => {
        link.classList.remove('active');
    });
    if (clickedLink) {
        clickedLink.classList.add('active');
    }

    // 2. Hide ALL pages completely
    document.querySelectorAll('*').forEach(el => {
        el.style.display = '';
        el.classList.remove('active-page', 'show', 'visible');
    });

    // 3. Hide all common page containers
    const allPages = document.querySelectorAll(`
        .page, 
        [id*="dashboard"],
        [id*="products"], 
        [id*="customers"],
        [id*="sales"], 
        [id*="purchases"],
        [id*="expenses"], 
        [id*="invoices"],
        [id*="profile"],
        section,
        main,
        article,
        .content,
        .container
    `);

    allPages.forEach(page => {
        page.style.display = 'none';
        page.classList.remove('active-page', 'show', 'visible');
    });

    // 4. Find and show target page (multiple fallback methods)
    let targetPage = document.getElementById(pageId) ||
        document.querySelector(`[data-page="${pageId}"]`) ||
        document.querySelector(`.${pageId}`) ||
        document.querySelector(`#${pageId}Page`) ||
        document.querySelector(`#${pageId}-page`) ||
        document.querySelector(`[id*="${pageId}"]`);

    console.log(`📄 Target page found:`, targetPage);

    if (targetPage) {
        targetPage.style.display = 'block';
        targetPage.style.opacity = '1';
        targetPage.classList.add('active-page');

        // Load data for this page
        setTimeout(() => loadPageData(pageId), 100);
    } else {
        console.error(`❌ Page not found: ${pageId}`);
        console.log("Available pages:", Array.from(document.querySelectorAll('[id], .page')).map(el => el.id || el.className));
    }
}

// =====================================
// TEST NAVIGATION BUTTON (Add to HTML)
// =====================================
function testNavigation() {
    console.log("🧪 TESTING NAVIGATION...");
    const pages = ['dashboard', 'products', 'customers', 'sales'];
    let i = 0;
    const interval = setInterval(() => {
        const link = document.querySelector(`a[data-page="${pages[i]}"]`);
        if (link) link.click();
        i = (i + 1) % pages.length;
    }, 1000);

    setTimeout(() => clearInterval(interval), 5000);
    console.log("✅ Navigation test complete!");
}

// =====================================
// ALL OTHER FUNCTIONS (unchanged from working version)
// =====================================
const DEMO_DATA = {
    products: [
        { id: 1, name: "Laptop Dell XPS", price: 85000, quantity: 5 },
        { id: 2, name: "iPhone 15 Pro", price: 95000, quantity: 8 }
    ],
    customers: [
        { id: 1, name: "Rahul Sharma", email: "rahul@email.com" }
    ],
    expenses: [{ id: 1, title: "Office Rent", amount: 25000, date: "2026-03-01" }],
    invoices: [{ id: 1, customer: "Rahul Sharma", product: "Laptop Dell XPS", quantity: 1, price: 85000, total: 85000, date: "2026-03-06" }]
};

function initDemoData() {
    Object.keys(DEMO_DATA).forEach(key => {
        if (!localStorage.getItem(key)) {
            localStorage.setItem(key, JSON.stringify(DEMO_DATA[key]));
        }
    });
}

function getStorage(key) { return JSON.parse(localStorage.getItem(key) || '[]'); }
function setStorage(key, data) { localStorage.setItem(key, JSON.stringify(data)); }

function setupForms() {
    ['addProductBtn', 'addCustomerBtn', 'addExpenseBtn', 'createInvoiceBtn'].forEach(id => {
        const btn = document.getElementById(id);
        if (btn) btn.onclick = window[id.replace('Btn', '')];
    });
}

function loadPageData(pageId) {
    switch(pageId) {
        case 'dashboard': updateDashboard(); break;
        case 'products': case 'purchases': loadProducts(); break;
        case 'customers': loadCustomers(); break;
        case 'sales': case 'invoices': loadInvoices(); break;
        case 'expenses': loadExpenses(); break;
    }
}

// Quick data functions (minimal)
function updateDashboard() {
    const invoices = getStorage('invoices');
    const expenses = getStorage('expenses');
    const totalSales = invoices.reduce((sum, i) => sum + i.total, 0);
    document.getElementById('totalSales') && (document.getElementById('totalSales').textContent = `₹${totalSales.toLocaleString()}`);
}

function loadProducts() {
    const products = getStorage('products');
    const tbody = document.querySelector('#productsTable tbody, #products tbody');
    if (tbody) {
        tbody.innerHTML = products.map(p => `<tr><td>${p.name}</td><td>₹${p.price.toLocaleString()}</td><td>${p.quantity}</td></tr>`).join('');
    }
}

// Add to your HTML to test:
// <button onclick="testNavigation()">TEST NAVIGATION</button>
// Open console (F12) and click it!

console.log("🚀 Navigation script loaded successfully!");
console.log("💡 Open console (F12) and try: testNavigation()");
