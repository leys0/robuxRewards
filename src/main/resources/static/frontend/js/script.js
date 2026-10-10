let selectedAmount = null;
let userNick = '';

function checkNickInput() {
    const nickInput = document.getElementById('roblox-nick').value.trim();
    const btnSearch = document.getElementById('btn-search-account');
    if (nickInput.length > 0) {
        btnSearch.disabled = false;
    } else {
        btnSearch.disabled = true;
    }
}

function updateProgress(step) {
    const bars = document.querySelectorAll('.progress-bar');
    bars.forEach((bar, index) => {
        if (index < step) {
            bar.classList.add('active');
        } else {
            bar.classList.remove('active');
        }
    });
}

function hideAllModals() {
    for (let i = 1; i <= 5; i++) {
        document.getElementById(`step-${i}-modal`).classList.add('hidden');
    }
}

function goToStep(step) {
    if (step === 2) {
        const nickInput = document.getElementById('roblox-nick').value.trim();
        userNick = nickInput ? (nickInput.startsWith('@') ? nickInput : '@' + nickInput) : '@Builderman';
        document.getElementById('display-nick').innerText = userNick;
    } else if (step === 4) {
        document.getElementById('mission-display-nick').innerText = userNick;
        document.getElementById('mission-display-robux').innerText = `${selectedAmount} Robux`;
    } else if (step === 5) {
        document.getElementById('final-user-nick').innerText = userNick;
    }

    hideAllModals();
    document.getElementById(`step-${step}-modal`).classList.remove('hidden');

    updateProgress(step);

    const titles = ['Nick', 'Conta', 'Robux', 'Missão', 'Envio'];
    document.getElementById('step-indicator').innerText = `Etapa ${step} de 5 · ${titles[step - 1]}`;
}

function selectRobux(element, amount) {
    document.querySelectorAll('.robux-card').forEach(card => {
        card.classList.remove('selected');
    });
    element.classList.add('selected');
    selectedAmount = amount;

    const btn = document.getElementById('btn-choose-amount');
    btn.disabled = false;
    btn.innerText = `Resgatar ${amount} Robux`;
}

function copyLink() {
    const linkToCopy = window.location.href;
    navigator.clipboard.writeText(linkToCopy).then(() => {
        const copyTextSpan = document.getElementById('copy-text');
        copyTextSpan.innerText = 'Link copiado!';
        setTimeout(() => {
            copyTextSpan.innerText = 'Copiar link';
        }, 2000);
    });
}

function copyProfile() {
    const profileText = document.getElementById('tiktok-profile-handle').innerText;
    navigator.clipboard.writeText(profileText).then(() => {
        const copyBtnSpan = document.getElementById('copy-profile-text');
        copyBtnSpan.innerText = 'Perfil copiado!';
        setTimeout(() => {
            copyBtnSpan.innerText = 'Copiar Perfil';
        }, 2000);
    });
}