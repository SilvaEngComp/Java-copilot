async function fetchExpenses() {
    const res = await fetch('/api/expenses');
    return res.json();
}
async function fetchTotal() {
    const res = await fetch('/api/expenses/total');
    return res.json();
}
async function addExpense(desc, amt) {
    await fetch('/api/expenses', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ description: desc, amount: parseFloat(amt) })
    });
}
async function deleteExpense(id) {
    await fetch(`/api/expenses/${id}`, { method: 'DELETE' });
}
async function updateExpense(id, desc, amt) {
    await fetch(`/api/expenses/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ description: desc, amount: parseFloat(amt) })
    });
}
function renderTable(expenses) {
    const tbody = document.querySelector('#expense-table tbody');
    tbody.innerHTML = '';
    expenses.forEach(exp => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td><span class="desc">${exp.description}</span></td>
            <td><span class="amt">${exp.amount.toFixed(2)}</span></td>
            <td>
                <button class="edit-btn">Edit</button>
                <button class="delete-btn">Delete</button>
            </td>
        `;
        // Edit button
        tr.querySelector('.edit-btn').onclick = async () => {
            const newDesc = prompt('Edit description:', exp.description);
            if (newDesc === null) return;
            const newAmt = prompt('Edit amount:', exp.amount);
            if (newAmt === null) return;
            await updateExpense(exp.id, newDesc, newAmt);
            await refresh();
        };
        // Delete button
        tr.querySelector('.delete-btn').onclick = async () => {
            if (confirm('Delete this expense?')) {
                await deleteExpense(exp.id);
                await refresh();
            }
        };
        tbody.appendChild(tr);
    });
}
async function refresh() {
    const expenses = await fetchExpenses();
    renderTable(expenses);
    const total = await fetchTotal();
    document.getElementById('total').textContent = total.toFixed(2);
}
document.getElementById('expense-form').onsubmit = async e => {
    e.preventDefault();
    const desc = document.getElementById('desc').value;
    const amt = document.getElementById('amt').value;
    await addExpense(desc, amt);
    document.getElementById('desc').value = '';
    document.getElementById('amt').value = '';
    await refresh();
};
refresh();

